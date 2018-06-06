import java.util.*;

public class Main {
	

	public static void main(String[] args) {

		HomeworkReader HR = new HomeworkReader(args[1]);
		Maze myMaze = HR.readData();	// create the maze
		HR.close();
		
		HomeworkWriter HW = new HomeworkWriter(args[2]);
		
		// solve each task
		if(args[0].equals("1"))
			myMaze.Navigate_Task1(HW);
		else if(args[0].equals("2"))
			myMaze.Navigate_Task2(HW);
		
		HW.close();
	}
		
}
