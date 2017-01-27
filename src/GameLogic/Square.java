package GameLogic;

public class Square extends PhysObject {
	//This class is to represent player characters.
	
	private int number;
	private int colour;
	
	public Square(int number, int colour) {
		this.number = number;
		this.colour = colour;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int getColour() {
		return colour;
	}

}
