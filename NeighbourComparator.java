import java.util.*;


/**
 * Compares 2 neighbours from the list of a Cell.
 * Useful for sorting the list of neighbours and finding the one with the highest priority
 * @author Dana
 *
 */
public class NeighbourComparator implements Comparator{

	String orientation;
	
	/**
	 * 
	 * @param orientation - useful when setting the priority between 2 cells with the same number of visits
	 */
	void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	

	@Override
	/**
	 * Compare based on the following criteria:
	 * - if one of the neighbours or both of the are not valid (a wall cell or out of the maze matrix)
	 * - the number of visits priority
	 * - the direction priority, based on the current orientation
	 */
	public int compare(Object o1, Object o2) {
		
		Neighbour n1 = (Neighbour) o1;
		Neighbour n2 = (Neighbour) o2;
		
	// one of the neighbours is Out cell
		if(n1.cell != null && n1.cell instanceof OutputCell)
			return -1;
		if(n2.cell != null && n2.cell instanceof OutputCell)
			return 1;
		
		
		
		// one of the neighbours or both of them out of ground (do not exist)
		if(n1.cell == null || n2.cell == null) {
			try {
				throw new HeroOutOfGroundException();
			}
			catch(HeroOutOfGroundException e) {	}
			finally {
			
				
			// if both are null 
				if(n1.cell == null && n2.cell == null)
					return 0;
				
				
			// one of them is null and one is wall	
				else if( (n1.cell == null && n2.cell instanceof WallCell)
						|| (n2.cell == null && n1.cell instanceof WallCell)
						)
					try {
						throw new CannotMoveIntoWallsException();
					}
					catch(CannotMoveIntoWallsException e){
						return 0;
					}
			// if one of them is null
				else if(n1.cell == null)
					return 1;
				else if(n2.cell == null)
					return -1;	
			}
		}
		
		
	// one of the neighbours or both of them are walls
		if(n1.cell instanceof WallCell || n2.cell instanceof WallCell) {

			try {
				throw new CannotMoveIntoWallsException();
			}
			catch(CannotMoveIntoWallsException e) {	}
			finally {

				if(n1.cell instanceof WallCell && n2.cell instanceof WallCell)
					return 0;
				else if(n1.cell instanceof WallCell)
					return 1;
				else if(n2.cell instanceof WallCell)
					return -1;
			}
			
		}
		
	// none if them is a wall or null
		if( ((NormalCell)(n1.cell)).numberOfVisits < ((NormalCell)(n2.cell)).numberOfVisits )
			return -1;
		else if ( ((NormalCell)(n1.cell)).numberOfVisits > ((NormalCell)(n2.cell)).numberOfVisits )
			return 1;
		else 
			return rotationPriotiry(n1.position, n2.position);

	}

	// for 2 neighbours with the same number of visits
	int rotationPriotiry(String position1, String position2) {
		
		int r1 = 0, r2 = 0;
		switch(orientation) {
		
		// NORTH
			case "north":
				switch(position1) {
				case "right":
					r1 = 1;
					break;
				case "up":
					r1 = 2;
					break;
				case "left":
					r1 = 3;
					break;
				case "down":
					r1 = 4;
					break;
				}
				
				switch(position2) {
				case "right":
					r2 = 1;
					break;
				case "up":
					r2 = 2;
					break;
				case "left":
					r2 = 3;
					break;
				case "down":
					r2 = 4;
					break;
				}
				
			break;
			
		// EAST
			case "east":
				switch(position1) {
				case "down":
					r1 = 1;
					break;
				case "right":
					r1 = 2;
					break;
				case "up":
					r1 = 3;
					break;
				case "left":
					r1 = 4;
					break;
				}
				
				switch(position2) {
				case "down":
					r2 = 1;
					break;
				case "right":
					r2 = 2;
					break;
				case "up":
					r2 = 3;
					break;
				case "left":
					r2 = 4;
					break;
				}
				
			break;
		
		// SOUTH
			case "south":
				switch(position1) {
				case "left":
					r1 = 1;
					break;
				case "down":
					r1 = 2;
					break;
				case "right":
					r1 = 3;
					break;
				case "up":
					r1 = 4;
					break;
				}
				
				switch(position2) {
				case "left":
					r2 = 1;
					break;
				case "down":
					r2 = 2;
					break;
				case "right":
					r2 = 3;
					break;
				case "up":
					r2 = 4;
					break;
				}
				
			break;
	
		// WEST	
			case "west":
				switch(position1) {
				case "up":
					r1 = 1;
					break;
				case "left":
					r1 = 2;
					break;
				case "down":
					r1 = 3;
					break;
				case "right":
					r1 = 4;
					break;
				}
				
				switch(position2) {
				case "up":
					r2 = 1;
					break;
				case "left":
					r2 = 2;
					break;
				case "down":
					r2 = 3;
					break;
				case "right":
					r2 = 4;
					break;
				}
				
			break;
		}
		
		if(r1 < r2)
			return -1;
		return 1;
	}
}
