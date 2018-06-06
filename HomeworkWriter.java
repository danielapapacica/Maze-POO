import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HomeworkWriter {
    
    private PrintWriter pw;
    
    /**
     * open file  
     * @param filename
     */
    public HomeworkWriter(String filename) {
        try {
            pw = new PrintWriter(new File(filename));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HomeworkWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * A method useful for printing in the file when needed
     * @param text - what we want to print
     */
    public void println(String text) {
        pw.println(text);
    }
    
    /**
     * close file
     */
    public void close() {
        pw.close();
    }
}

