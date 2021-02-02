/**
 * Class:           Writer
 * Description:     Manages file writing
 *
 * @extends Node
 * @authors: Matias Villarroel, Víctor Garrido
 */
package utilities;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    /**
     * Writes a string into a file
     * @param fileName where we write
     * @param text we want to write
     */
    public static void writeToFile(String fileName, String text){

        try {
            FileWriter fw = new FileWriter(Config.PATH + fileName + Config.EXTENSION, true);
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
