/**
 * 
 * @author Dana
 * Contains the position of the current node in the maze, the previous node (which contains the previous position) and the length of the route so far
 */
public class CellNode {

	Position position;
	CellNode previous;
	int length;	// number of cells passed so far
	
	/**
	 * 
	 * @param position - the position arrived
	 * @param previous	- previous node
	 * @param prevLength - length contained in the previous node
	 */
	public CellNode(Position position, CellNode previous, int length) {
		this.position = position;
		this.previous = previous;
		this.length = length;
	}
}
