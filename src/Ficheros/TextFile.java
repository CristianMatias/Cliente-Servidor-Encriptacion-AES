package Ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * This class is meant to manage the writing and reading of a text file
 * @author Manuel
 */
public class TextFile {
    /**
     * This method waits for a path with extension and the string which will be
     * write in the file
     * @param path Path where the file will be created. Requires file name and extension
     * @param lines Info which will be saved
     * @return true if the file was successfully created and fill
     */
    public boolean write(String path, String lines) {
        File file = new File(path);
        
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(file));
            bf.write(lines);
            bf.flush();
            bf.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    /**
     * This method reads a file expecting the file path
     * @param path File path with extension. Example: .txt, .json
     * @return String from file
     */
    public String read(String path){
        File file = new File(path);
        String line;
        String text = "";
        
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
        while((line = bf.readLine()) != null){
            text = text + line + "\n";
        }
        bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    
    public boolean exists(String path){
        File file = new File(path);
        
        if(file.exists()){
            return true;
        }
        return false;
    }
}
