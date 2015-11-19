
public class Cell {
	private int alive;
	private char [] shape = { '-', '@'};
	
	
	Cell(){
		this.setAlive(0);
	}

	public int getAlive() {
		return alive;
	}

	public void setAlive(int alive) {
		this.alive = alive;
	}
	
	public char getAliveShape() {
		return shape[0];
	}
	
	public char getDeadShape() {
		return shape[1];
	}
	
	public char getCurrentShape() {
		return this.shape[this.alive];
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
