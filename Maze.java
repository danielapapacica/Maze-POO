import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;


/**
 * 
 * @author Dana
 * This class incorporates the maze (and other useful object), and a method for each task
 */
public class Maze {
	
	int height, width;
	Cell[][] matrix;
	
	Position currentPosition;
	
	// north, south, east, west
	String orientation;
	
	CellFactory factory = CellFactory.getInstance();
	
	NeighbourComparator comp = new NeighbourComparator();
	
	
	/**
	 * - set height, width, current position, orientation
	 * - create the Cell matrix
	 * - set for each cell in the matrix a list of neighbours
	 * @param height- height of the cell matrix
	 * @param width - width if the cell matrix
	 * @param symbolMatrix - an array of strings; each array is a a line of the matrix of symbols(.,#,I,O) read from the file
	 */
	public Maze(int height, int width, String[] symbolMatrix) {
		
	// set height, width, orientation
		this.height = height;
		this. width = width;
		matrix = new Cell[height][width];
		orientation = "north";
		
	// create the cell matrix
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j ++) {
				matrix[i][j] = factory.createCell(symbolMatrix[i].charAt(j));
				
				// set position for the hero to start from
				if(symbolMatrix[i].charAt(j) == 'I') {
					currentPosition = new Position(i,j);
				}
			}
		}
		
	// set all neighbours for each cell
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j ++) {

				if(matrix[i][j] instanceof NormalCell) {
					
					Cell up, down, left, right;
					
					if(i == 0)
						up = null;
					else
						up = matrix[i-1][j];
					
					if(i == height-1)
						down = null;
					else
						down = matrix[i+1][j];
					
					if(j == 0)
						left = null;
					else
						left = matrix[i][j-1];
					
					if(j == width-1)
						right = null;
					else
						right = matrix[i][j+1];
						
					((NormalCell)matrix[i][j]).setNeighbours(up, down, right, left);
				}
			}
		}
		
	}
	
	

	/**
	 * Method for solving TASK 1
	 * @param HW - Homework Writer for printing the solution in the file
	 */
	public void Navigate_Task1(HomeworkWriter HW){
		
	// list of all the positions where the hero arrives while searching for the exit cell
		ArrayList<Position> route = new ArrayList<Position>();
		
		boolean finish = false;
		int moves = 0;
		
		while(!finish) {
			moves++;
			int x = currentPosition.x;
			int y = currentPosition.y;
			((NormalCell)matrix[x][y]).numberOfVisits++;
			
		// if there is a way out of the maze it is not normal for a cell to be visited more than 4 times
			if(((NormalCell)matrix[x][y]).numberOfVisits > 4) {
				try {
					throw new NoWayOutException();
				}
				catch(NoWayOutException e) {}
			}
			
			route.add(new Position(x, y)); 	// add every new position to the list
			
			comp.setOrientation(orientation); 	// set the comparator with the current orientation 
												// 	because the priority depends on the orientation of the hero

			// sort neighbours of the cells by their priority
			Collections.sort( ((NormalCell)matrix[x][y]).neighbours, comp  );
			

			String futurePosition = ((NormalCell)matrix[x][y]).neighbours.get(0).position;
			
			// if arrived at the output cell
			if( ((NormalCell)matrix[x][y]).neighbours.get(0).cell instanceof OutputCell)
				finish = true;
			
			// set the new current position
			switch(futurePosition) {
			case "up":
				currentPosition.x--;
				orientation = "north";
				break;
			case "down":
				currentPosition.x++;
				orientation = "south";
				break;
			case "right":
				currentPosition.y++;
				orientation = "east";
				break;
			case "left":
				currentPosition.y--;
				orientation = "west";
				break;
			}

			
		}
		
		// the output cell
		route.add(currentPosition);
		moves++;
		
		// print all the positions of the hero in the maze while searching for the output cell
		HW.println(moves + "");
		for (int i = 0; i < route.size(); i++) {
			Position p = route.get(i);
			HW.println(p.x + " " + p.y);
		}
	}


	/**
	 * Method for solving TASK 2
	 * @param HW - Homework Writer for printing the solution in the file
	 */
	public void Navigate_Task2(HomeworkWriter HW){
		
		// the neighbours of each cell will be transformed into nodes which will be put in this queue
		LinkedList<CellNode> nodeQueue = new LinkedList<CellNode>();
		
		// a reference matrix
		// we will mark 1 in this matrix in the position where a cell in the maze matrix have been visited
		int visitMatrix[][] = new int[height][width];
		
		boolean finish = false;
		
		// the first node we will put in the queue
		CellNode firstNode = new CellNode(currentPosition, null, 0);
		nodeQueue.addLast(firstNode);
		visitMatrix[currentPosition.x][currentPosition.y] = 1;
		
		// this will be the node containing the position of the output cell
		CellNode finishNode = null;
		
		while(!finish && !nodeQueue.isEmpty()) {
			CellNode node = nodeQueue.removeFirst();	
		// Put neighbours in the queue
			

			//RIGHT neighbour
			if(node.position.y == width - 1) {
				try {
					throw new HeroOutOfGroundException();
				}
				catch(HeroOutOfGroundException e) {}
			}
			else if(matrix[node.position.x][node.position.y + 1] instanceof WallCell) {
				try {
					throw new CannotMoveIntoWallsException();
				}
				catch(CannotMoveIntoWallsException e) {}
			}
			else if(visitMatrix[node.position.x][node.position.y + 1] != 1){
				
				Position newPosition = new Position(node.position.x, node.position.y + 1);
				CellNode newNode = new CellNode(newPosition, node, node.length + 1);
				
				// if the neighbour is the output cell
				if(matrix[node.position.x][node.position.y + 1] instanceof OutputCell) {
					finish = true;
					finishNode = newNode;
				}
	
				nodeQueue.addLast(newNode);
				visitMatrix[node.position.x][node.position.y + 1] = 1;
			}


			// UP neighbour
			if(node.position.x == 0) {
				try {
					throw new HeroOutOfGroundException();
				}
				catch(HeroOutOfGroundException e) {}
			}
			else if(matrix[node.position.x - 1][node.position.y] instanceof WallCell) {
				try {
					throw new CannotMoveIntoWallsException();
				}
				catch(CannotMoveIntoWallsException e) {}
			}
			else if(visitMatrix[node.position.x - 1][node.position.y] != 1){
				
				Position newPosition = new Position(node.position.x - 1, node.position.y);
				CellNode newNode = new CellNode(newPosition, node, node.length + 1);
				
				// if the neighbour is the output cell
				if(matrix[node.position.x - 1][node.position.y] instanceof OutputCell){
					finish = true;
					finishNode = newNode;
				}
				
				nodeQueue.addLast(newNode);
				visitMatrix[node.position.x - 1][node.position.y] = 1;
			}

			
			// LEFT neighbour
			if(node.position.y == 0) {
				try {
					throw new HeroOutOfGroundException();
				}
				catch(HeroOutOfGroundException e) {}
				}
			else if(matrix[node.position.x][node.position.y - 1] instanceof WallCell) {
				try {
					throw new CannotMoveIntoWallsException();
					}
				catch(CannotMoveIntoWallsException e) {}
			}
			else if(visitMatrix[node.position.x][node.position.y - 1] != 1){
				
				Position newPosition = new Position(node.position.x, node.position.y - 1);
				CellNode newNode = new CellNode(newPosition, node, node.length + 1);
	
				// if the neighbour is the output cell
				if(matrix[node.position.x][node.position.y - 1] instanceof OutputCell) {
					finish = true;
					finishNode = newNode;
				}
				
				nodeQueue.addLast(newNode);
				visitMatrix[node.position.x][node.position.y - 1] = 1;
			}
			
			
			// DOWN neighbour
			if(node.position.x == height - 1) {
				try {
					throw new HeroOutOfGroundException();
				}
				catch(HeroOutOfGroundException e) {}
			}
			else if(matrix[node.position.x + 1][node.position.y] instanceof WallCell) {
				try {
					throw new CannotMoveIntoWallsException();
				}
				catch(CannotMoveIntoWallsException e) {}
			}
			else if(visitMatrix[node.position.x + 1][node.position.y] != 1) {
				
				Position newPosition = new Position(node.position.x + 1, node.position.y);
				CellNode newNode = new CellNode(newPosition, node, node.length + 1);
				
				// if the neighbour is the output cell
				if(matrix[node.position.x + 1][node.position.y] instanceof OutputCell){
					finish = true;
					finishNode = newNode;
				}

				nodeQueue.addLast(newNode);
				visitMatrix[node.position.x + 1][node.position.y] = 1;
			}			
		}
		
		
		// if there's no way out of the maze...
		if(!finish) {
			try {
				throw new NoWayOutException();
			}
			catch(NoWayOutException e) {}
		}
		
		// in this stack we will put each position of each previous node of the node that arrived at the out cell
		Stack<Position> positionStack = new Stack<Position>();
		int length = 0;
		CellNode prevNode = finishNode;
		
		while(prevNode != null) {
			positionStack.add(prevNode.position);
			prevNode = prevNode.previous;
			length++;
		}
		
	// write the results
		HW.println(length + "");
		
		while(!positionStack.isEmpty()) {
			Position p = positionStack.pop();
			HW.println(p.x + " " + p.y);
		}
	}
	
}
