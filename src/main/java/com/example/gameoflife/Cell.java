package com.example.gameoflife;

public class Cell {
        public static final int SPECIES_NORMAL = 0;
        public static final int SPECIES_COEXISTENT = 1;
        public static final int SPECIES_AGGRESSIVE = 2;

        private int alive;
        private int species;
        private char [] shape = { '-', '@', 'C', 'X'};
	
	
        Cell(){
                this.setAlive(0);
                this.species = SPECIES_NORMAL;
        }

	public int getAlive() {
		return alive;
	}

        public void setAlive(int alive) {
                this.alive = alive;
        }

        public int getSpecies() {
                return species;
        }

        public void setSpecies(int species) {
                this.species = species;
        }
	
        public char getAliveShape() {
                return shape[species + 1];
        }

        public char getDeadShape() {
                return shape[0];
        }

        public char getCurrentShape() {
                if (this.alive == 0) {
                        return shape[0];
                }
                return this.shape[this.species + 1];
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
