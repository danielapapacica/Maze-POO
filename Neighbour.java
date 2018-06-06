
public class Neighbour {
	
	String position;	// position from the current cell
						// "up"/"down"/"left"/"right"
	
	Cell cell;
	
	public Neighbour(Cell cell, String position) {
		this.cell = cell;
		this.position = position;
	}
}
