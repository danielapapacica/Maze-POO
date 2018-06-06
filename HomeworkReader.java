
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class HomeworkReader {
    
    private BufferedReader bf;

    
  /**
   * open file  
   * @param filename
   */
    public HomeworkReader(String filename) {
        try {
            bf = new BufferedReader(new FileReader(new File(filename)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HomeworkReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
	/**
	 * read data and stock it the Maze
	 * @return the Maze
	 */
    public Maze readData(){
        Maze result = null;
        
        try {  
           String[] dimensions = bf.readLine().split(" ");
           int height = Integer.parseInt(dimensions[0]);
           int width = Integer.parseInt(dimensions[1]);
           
           String[] symbolMatrix = new String[height];
           for(int i = 0; i < height; i++) {
        	   symbolMatrix[i] = bf.readLine();
           }

           result = new Maze(height, width, symbolMatrix);
            
        } catch (IOException ex) {
            Logger.getLogger(HomeworkReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }

    
    /**
     * close file
     */
    public void close() {
        try {
            bf.close();
        } catch (IOException ex) {
            Logger.getLogger(HomeworkReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
