abstract class Cell{
	
}

/**
 * Helps creating the matrix of Cells of the class Maze
 * Each Cell must be created depending on the symbol(#,.,I,O) from the matrix read from file
 * @author Dana
 *
 */
public class CellFactory {
	
	private static final CellFactory INSTANCE = new CellFactory();
	
	private CellFactory() {
	}
	
	public static CellFactory getInstance() {
		return INSTANCE;
	}
	
	/**
	 * 
	 * @param symbol - One of these symbols: "#", ".", "O", "I"
	 * @return a specific type of Cell
	 */
	public Cell createCell(char symbol) {
		
		Cell cell = null;
		switch(symbol) {

		case '.':
		case 'I':	// the input cell is also a normal cell, but just the first one the hero steps into
			cell = new NormalCell();
			break;
			
		case '#':
			cell = new WallCell();
			break;
			
		case 'O':
			cell = new OutputCell();
			break;
		}
		
		return cell;

	}
}
