package com.example.gameoflife;
import java.util.Random;
import java.util.Stack;

public class Petridish {
	private final int PETRI_SIZE_ROWS = 40;
	private final int PETRI_SIZE_COLS = 35;
	private Cell [][] cellArena;
	private int cellGeneration;

	Petridish(){
		this.cellArena = new Cell[PETRI_SIZE_COLS][PETRI_SIZE_ROWS];
		for(int columns = 0; columns < PETRI_SIZE_COLS; columns++){
			for(int rows = 0; rows < PETRI_SIZE_ROWS; rows++){
				this.cellArena[columns][rows] = new Cell();
			}
		}
	}
	
	/*
	 * disply()
	 * 
	 * This method has no parameters.
	 * 
	 * This method is a simple display method using ASCII characters to render
	 * a formatted rectangular petridish.
	 * 
	 * A minimized sample of the formatted output.
	 * 
	 * 	|########################################|
		|----------------------------------------|
		|---------------------------@------------|
		|---------------------@@-----------------|
		|--------------------@--@----------------|
		|---------------------@@-----------------|
		|----------------------------------------|
		|----------------------------------------|
		|------@@@-----------@-------------------|
		|--------@-----------@-------------------|
		|-------@------------@-------------------|
		|----------------------------------------|
		|########################################|
	 *	Generation: 10
	 * 
	 */ 
	public void display(){
		System.out.printf("%c%s%c%n", '|', "########################################", '|');
		for(int columns = 0; columns < PETRI_SIZE_COLS; columns++){
			System.out.printf("%c", '|');
			for(int rows = 0; rows < PETRI_SIZE_ROWS; rows++){
				System.out.print(this.cellArena[columns][rows].getCurrentShape());
			}
			System.out.printf("%c%n", '|');
		};
		System.out.printf("%c%s%c%n", '|', "########################################", '|');
		System.out.printf("Generation: %d%n", this.cellGeneration++);
	}
	/*
	 * startLife()
	 * 
	 * This method takes an integer parameter 'seed'
	 * 
	 * The seed integer gives the user a small amount of 
	 * control in giving the simulation a higher chance of iterating
	 * before settling into a stable state.
	 * 
	 * A object of the Random class is used to fill the petridish 2d array 
	 * randomly with a number of cells specified to be the width and height of
	 * the petridish in addition to the value that is stored in the seed variable.
	 * 
	 * This function returns nothing, however, a side effect is that once the 
	 * petridish is populated, the display() function is called to print the array
	 * to the terminal.
	 * 
	 */
	public void startLife(int seed){
		if((seed < 0 || seed > 40))
			System.err.println("Seed was too big or too small" +
								"\nSetting max allowable seed (40)");
		
		Random rand = new Random();
		int rnum1 = 0;
		int rnum2 = 0;
		for(int i = 0; i < this.PETRI_SIZE_COLS + this.PETRI_SIZE_ROWS + seed; ){
			rnum1 = (0 + (1 + rand.nextInt(this.PETRI_SIZE_COLS-1)));
			rnum2 = (0 + (1 + rand.nextInt(this.PETRI_SIZE_ROWS-1)));
                        if(this.cellArena[rnum1][rnum2].getAlive() != 1){
                                this.cellArena[rnum1][rnum2].makeAlive();
                                this.cellArena[rnum1][rnum2].setSpecies(CellSpecies.NORMAL);
                                i++;
                        }
		}
		this.display();
	}
	
	private void checkLifeConditions(){
		/*
		 * 1)Any live cell with less than two live neighbours dies, as if from under population.
		 * 2)Any live cell with two or three live neighbours lives on to the next generation.
		 * 3)Any live cell with more than three live neighbours dies, as if by overcrowding.
		 * 4)Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
		 * 
		 */

		Stack<CellPosition> listOfDeadCells = new Stack<CellPosition>();
		Stack<CellPosition> listOfNewCells = new Stack<CellPosition>();

		this.determineDyingCells(listOfDeadCells);
		this.spawnNewCells(listOfNewCells);
                while(!listOfNewCells.isEmpty()){
                        CellPosition pos = listOfNewCells.pop();
                        Cell newCell = this.cellArena[pos.column][pos.row];
                        newCell.makeAlive();
                        newCell.setSpecies(CellSpecies.NORMAL);
                }
		while(!listOfDeadCells.isEmpty()){
			this.cellArena[listOfDeadCells.peek().column][listOfDeadCells.peek().row].makeDead();
			listOfDeadCells.pop();
		}
	}
	/*
	 * determineDyingCells()
	 * 
	 * This is the logical approximation of the first
	 * and third rules in Conway's Game of Life:
	 * 
	 * 1)Any live cell with less than two live neighbours dies, as if from under population.
	 * 3)Any live cell with more than three live neighbours dies, as if by overcrowding.
	 * 
	 * This method takes a stack object of type CellPosition and calls the checkNighbors() method,
	 * which it then uses to determine what cells should be selected to be made dead. Since the
	 * checkneighbors() function returns true if there exists 2 or 3 cells, the function's returned
	 * boolean value is negated in order to determine if the first and third rule apply to this 
	 * iteration.
	 * 
	 * The method begins by declaring a inner class called 
	 * CellPosition which has two integer fields: 
	 * 1) row
	 * 2) column
	 * 
	 * This inner class is responsible for keeping track of cells that are selected to
	 * be made dead. 
	 * 
	 * The Stack of type CellPosition is used to contain the many possible instances of
	 * CellPosition.
	 */
	private void determineDyingCells(Stack<CellPosition> listOfDeadCells){
		
		for(int rows = 0; rows < PETRI_SIZE_ROWS; rows++){
			for(int columns = 0; columns < PETRI_SIZE_COLS; columns++){
                                if(this.cellArena[columns][rows].getAlive() == 1 &&
                                   this.cellArena[columns][rows].getSpecies() != CellSpecies.COEXISTENT){
                                        if(!this.checkNeighbors(columns, rows)){
                                                listOfDeadCells.push(new CellPosition(rows, columns));
                                        }
                                }
			}
		}
	}
	/*
	 * spawnNewCells()
	 * 
	 * This is the logical approximation of the fourth
	 * rule in Conway's Game of Life:
	 * 
	 * 4)Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
	 * 
	 * This method takes a stack object of type CellPosition and calls the, 
	 * checkNighborsExactlyThree() method, which it then uses to determine what
	 * cells should be selected to be made alive.
	 * 
	 * The method begins by declaring an object of the CellPosition class
	 * which has two integer fields: 
	 * 1) row
	 * 2) column
	 * 
	 * This inner class is responsible for keeping track of cells(two dimensional element) that 
	 * are selected to be made alive.
	 * 
	 * The Stack of type CellPosition is passed in and used to contain the many potential instances of
	 * CellPosition.
	 */
	private void spawnNewCells(Stack<CellPosition> listOfNewCells){
		for(int rows = 0; rows < PETRI_SIZE_ROWS; rows++){
			for(int columns = 0; columns < PETRI_SIZE_COLS; columns++){
				if(this.cellArena[columns][rows].getAlive() == 0){
					if(this.checkNeighborsExactlyThree(columns, rows)){
						//System.out.printf("Success. It works at: row %d | column %d%n", rows, columns);
						listOfNewCells.push(new CellPosition(rows, columns));
					}
				}
			}
		}
	}
	
	/*
	 * checkNeighbors()
	 * 
	 * This method takes takes two parameters, an integer column, and an integer row.
	 * 
	 * This method is responsible for checking the number
	 * of neighboring cells to the current cell to determine if there are at 
	 * at least two but not more than three live cells bordering the current cell.
	 * 
	 * It returns true if there are either 2 or 3 cells neighboring the current cell element.
	 * If there are none, it returns false.
	 * 
	 */ 
	private boolean checkNeighbors(int column, int row){
		int numNeighbors = 0;			//First row and away from edges
		if(row == 0 && (column >= 1 && column <= PETRI_SIZE_COLS-2)){
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors >= 2 && numNeighbors <= 3)
				return true;
		}								//Top left
		else if(row == 0 && column == 0){
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors >= 2 && numNeighbors <= 3)
				return true;
		}								//Top right
		else if(row == 0 && column == this.PETRI_SIZE_COLS-1){
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors >= 2 && numNeighbors <= 3)
				return true;
		}								//Center area and away from edges
		else if((row >= 1 && row <= this.PETRI_SIZE_ROWS-2) && 
				(column >= 1 && column <= this.PETRI_SIZE_COLS-2)){
			if(this.cellArena[column-1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row-1].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors >= 2 && numNeighbors <= 3)
				return true;
		}								//Non first/last row-wise but touching left edge column-wise
		else if((row >= 1 && row <= PETRI_SIZE_ROWS-2) && 
				(column == 0)){
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors >= 2 && numNeighbors <= 3)
				return true;
		}								//Non first/last row-wise but touching right edge column-wise
		else if((row >= 1 && row <= this.PETRI_SIZE_ROWS-2) && 
				(column == this.PETRI_SIZE_COLS-1)){
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors >= 2 && numNeighbors <= 3)
				return true;
		}								//Last row away from the edges
		else if(row == this.PETRI_SIZE_ROWS-1 && (column >= 1 && column <= this.PETRI_SIZE_COLS-2)){
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors >= 2 && numNeighbors <= 3)
				return true;
		}								//Bottom left
		else if(row == this.PETRI_SIZE_ROWS-1 && column == 0){
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors >= 2 && numNeighbors <= 3)
				return true;
		}  								//Bottom right
		else if(row == this.PETRI_SIZE_ROWS-1 && column == this.PETRI_SIZE_COLS-1){
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors >= 2 && numNeighbors <= 3)
				return true;
		}
		else{
			System.err.printf("Error occured in checkNighbors() at: row %d | column %d%n", row, column);
		}
		return false;
	}
	/*
	 * checkNeighborsExactlyThree()
	 * 
	 * This method takes takes two parameters, an integer column, and an integer row.
	 * 
	 * This method is responsible for checking if the number
	 * of neighboring cells around the current cell is exactly three.
	 * 
	 * This method is a specially written case in conjunction with the spawnNewCells() function above
	 * in order to determine if the application of the fourth rule applies, where there must be exactly
	 * 3 cells adjacent to current cell element of the Petridish.
	 * 
	 * It returns true if there are either 2 or 3 cells neighboring the current cell element.
	 * If there are none, it returns false.
	 * 
	 */ 
        private boolean checkNeighborsExactlyThree(int column, int row){
		int numNeighbors = 0;			//First row and away from edges
		if(row == 0 && (column >= 1 && column <= this.PETRI_SIZE_COLS-2)){
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors == 3)
				return true;
		}								//Top left
		else if(row == 0 && column == 0){
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors == 3)
				return true;
		}								//Top right
		else if(row == 0 && column == this.PETRI_SIZE_COLS-1){
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors == 3)
				return true;
		}								//Center area and away from edges
		else if((row >= 1 && row <= this.PETRI_SIZE_ROWS-2) && 
				(column >= 1 && column <= this.PETRI_SIZE_COLS-2)){
			if(this.cellArena[column-1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row-1].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors == 3)
				return true;
		}								//Non first/last row-wise but touching left edge column-wise
		else if((row >= 1 && row <= this.PETRI_SIZE_ROWS-2) && 
				(column == 0)){
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors == 3)
				return true;
		}								//Non first/last row-wise but touching right edge column-wise
		else if((row >= 1 && row <= this.PETRI_SIZE_ROWS-2) && 
				(column == this.PETRI_SIZE_COLS-1)){
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row+1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row+1].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors == 3)
				return true;
		}								//Last row away from the edges
		else if(row == this.PETRI_SIZE_ROWS-1 && (column >= 1 && column <= this.PETRI_SIZE_COLS-2)){
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors == 3)
				return true;
		}								//Bottom left
		else if(row == this.PETRI_SIZE_ROWS-1 && column == 0){
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column+1][row].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors == 3)
				return true;
		}  								//Bottom right
		else if(row == this.PETRI_SIZE_ROWS-1 && column == this.PETRI_SIZE_COLS-1){
			if(this.cellArena[column][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row-1].getAlive() == 1)
				numNeighbors++;
			if(this.cellArena[column-1][row].getAlive() == 1)
				numNeighbors++;
			if(numNeighbors == 3)
				return true;
		}
		else{
			System.err.printf("Error occured in checkNighborsExactlyThree() at: row %d | column %d%n", row, column);
		}
                return false;
        }

        /*
         * applyCoexistentBehavior()
         *
         * Coexistent cells replicate one neighbouring live cell into a
         * neighbouring empty location without killing any cells.
         */
        private void applyCoexistentBehavior(){
                for(int r = 0; r < PETRI_SIZE_ROWS; r++){
                        for(int c = 0; c < PETRI_SIZE_COLS; c++){
                                Cell cell = this.cellArena[c][r];
                                if(cell.getAlive() == 1 && cell.getSpecies() == CellSpecies.COEXISTENT){
                                        CellPosition copySource = null;
                                        CellPosition emptySpot = null;
                                        for(int dr = -1; dr <= 1; dr++){
                                                for(int dc = -1; dc <= 1; dc++){
                                                        if(dr == 0 && dc == 0) continue;
                                                        int nr = r + dr;
                                                        int nc = c + dc;
                                                        if(nr < 0 || nr >= PETRI_SIZE_ROWS || nc < 0 || nc >= PETRI_SIZE_COLS)
                                                                continue;
                                                        Cell neighbor = this.cellArena[nc][nr];
                                                        if(neighbor.getAlive() == 1 && copySource == null){
                                                                copySource = new CellPosition(nr, nc);
                                                        }
                                                        if(neighbor.getAlive() == 0 && emptySpot == null){
                                                                emptySpot = new CellPosition(nr, nc);
                                                        }
                                                }
                                        }
                                        if(copySource != null && emptySpot != null){
                                                Cell source = this.cellArena[copySource.column][copySource.row];
                                                Cell dest = this.cellArena[emptySpot.column][emptySpot.row];
                                                dest.makeAlive();
                                                dest.setSpecies(source.getSpecies());
                                        }
                                }
                        }
                }
        }

        /*
         * applyAggressiveBehavior()
         *
         * Aggressive cells consume neighbouring non-aggressive live cells.
         * For every two cells consumed a new aggressive cell spawns in an
         * adjacent empty location.
         */
        private void applyAggressiveBehavior(){
                for(int r = 0; r < PETRI_SIZE_ROWS; r++){
                        for(int c = 0; c < PETRI_SIZE_COLS; c++){
                                Cell cell = this.cellArena[c][r];
                                if(cell.getAlive() == 1 && cell.getSpecies() == CellSpecies.AGGRESSIVE){
                                        int eaten = 0;
                                        Stack<CellPosition> empty = new Stack<>();
                                        for(int dr = -1; dr <= 1; dr++){
                                                for(int dc = -1; dc <= 1; dc++){
                                                        if(dr == 0 && dc == 0) continue;
                                                        int nr = r + dr;
                                                        int nc = c + dc;
                                                        if(nr < 0 || nr >= PETRI_SIZE_ROWS || nc < 0 || nc >= PETRI_SIZE_COLS)
                                                                continue;
                                                        Cell neighbor = this.cellArena[nc][nr];
                                                        if(neighbor.getAlive() == 1 && neighbor.getSpecies() != CellSpecies.AGGRESSIVE){
                                                                neighbor.makeDead();
                                                                eaten++;
                                                        } else if(neighbor.getAlive() == 0){
                                                                empty.push(new CellPosition(nr, nc));
                                                        }
                                                }
                                        }
                                        int spawn = eaten / 2;
                                        while(spawn > 0 && !empty.isEmpty()){
                                                CellPosition pos = empty.pop();
                                                Cell target = this.cellArena[pos.column][pos.row];
                                                target.makeAlive();
                                                target.setSpecies(CellSpecies.AGGRESSIVE);
                                                spawn--;
                                        }
                                }
                        }
                }
        }
	/*
	 * checkLivingCells()
	 * 
	 * This method takes takes no parameters.
	 * 
	 * This method is responsible for checking the number
	 * of living cells in the current iteration of the simulation.
	 * 
	 * TODO: 1) Figure out how to pause the simulation when still life is achieved
	 * 		 2) Figure out how to account for life the is period based
	 * 
	 * It returns true if there are any living cells.
	 * If there are none, it returns false.
	 * 
	 */ 
	private boolean checkLivingCells(){
		int numCells = 0;
		for(int rows = 0; rows < PETRI_SIZE_ROWS; rows++){
			for(int columns = 0; columns < PETRI_SIZE_COLS; columns++){
				if(this.cellArena[columns][rows].getAlive() == 1){
					numCells++;
				}
			}
		}
		if(numCells > 0){
			return true;
		}
		return false;
	}
	/*
	 * iterateLife()
	 * 
	 * This method takes takes no parameters.
	 * 
	 * This method is responsible for iterating through the simulation. The process
	 * is forced to sleep to allow the user a chance to watch the bacteria grow at a 
	 * steady pace. A limit on how many iterations can happen is also included until 
	 * logic for terminating the thread once the simulation reaches a stable state.
	 * 
	 * This method returns nothing, but is responsible for stopping the simulation loop
	 * and returning back to the GameofLife scope.
	 * 
	 */ 
        public void iterateLife() throws Exception {
                int timer = 0;
                while(this.checkLivingCells()){
                        this.checkLifeConditions();
                        this.applyCoexistentBehavior();
                        this.applyAggressiveBehavior();
                        this.display();
                        Thread.sleep(250);
			timer++;
			if(timer == 400){
				break;
			}
		}
	}
}
