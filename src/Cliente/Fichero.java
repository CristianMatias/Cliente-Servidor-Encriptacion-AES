package Cliente;

import java.io.*;

/**
 * This class will read a csv file and return their different
 * rows, in this case it will be used to read some names and their
 * associated ip
 * GLOBAL VARIABLES:
 *      - lectorFichero: is the FileReader object
 *      - direccionFichero: is the direction where is the file
 *      - lectorLineas: is the BufferedReader object
 * @author Roberto
 */
public class Fichero {

    private FileReader lectorFichero;
    private String direccionFichero;
    private BufferedReader lectorLineas;

    /**
     * The constructor, it receives the direction of the file that will be readen
     * @param direccionFichero the file direction
     * @throws FileNotFoundException if there is no file
     */
    public Fichero(String direccionFichero) throws FileNotFoundException {
        this.direccionFichero = direccionFichero;
        lectorFichero = new FileReader(new File(direccionFichero));
        lectorLineas = new BufferedReader(lectorFichero);
    }

    /**
     * Reads the file and return the content split by ','
     * the optimize way to use is to read an csv
     * @return the rows of the csv
     * @throws IOException if there is an error while reading the file
     */
    public String[] leer() throws IOException {
        String linea;
        String direccionesIP = "";

        while((linea = lectorLineas.readLine()) != null){
            direccionesIP += linea;
        }
        return direccionesIP.split(",");
    }


}
