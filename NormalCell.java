import java.util.*;


/**
 * The cell where we can navigate
 * @author Dana
 *
 */
public class NormalCell extends Cell{
	
	int numberOfVisits;
	
	// the cells neighbours of the current cell
	ArrayList<Neighbour> neighbours = new ArrayList<Neighbour>();
	
	/**
	 * Sets all the neighbour cells around the current cell
	 * @param up - the cell above
	 * @param down - the cell below
	 * @param right - the cell at the right side
	 * @param left - the cell at the left side
	 */
	public void setNeighbours(Cell up, Cell down, Cell right, Cell left) {		
		neighbours.add(new Neighbour(up, "up"));
		neighbours.add(new Neighbour(down, "down"));
		neighbours.add(new Neighbour(left, "left"));
		neighbours.add(new Neighbour(right, "right"));
		
	}
}
