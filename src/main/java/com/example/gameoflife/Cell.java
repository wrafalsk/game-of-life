package com.example.gameoflife;

public class Cell {

        private int alive;
        private CellSpecies species;
        private char [] shape = { '-', '@', 'C', 'X'};


        Cell(){
                this.setAlive(0);
                this.species = CellSpecies.NORMAL;
        }

	public int getAlive() {
		return alive;
	}

        public void setAlive(int alive) {
                this.alive = alive;
        }

        public CellSpecies getSpecies() {
                return species;
        }

        public void setSpecies(CellSpecies species) {
                this.species = species;
        }
	
        public char getAliveShape() {
                return shape[species.ordinal() + 1];
        }

        public char getDeadShape() {
                return shape[0];
        }

        public char getCurrentShape() {
                if (this.alive == 0) {
                        return shape[0];
                }
                return this.shape[this.species.ordinal() + 1];
        }
	
	public void makeAlive(){
		this.alive = 1;
	}
	
	public void makeDead(){
		this.alive = 0;
	}
	
	@Override
	public String toString(){
		return "" + this.getCurrentShape();
		
	}

}
