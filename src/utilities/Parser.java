/**
 * Class:           Parser
 * Description:     Parses transactions files into transaction
 * @authors: Matias Villarroel, Víctor Garrido
 */
package utilities;

import model.Msg;
import model.Transaction;
import model.TransactionType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Parser {

    public static ArrayList<Transaction> parseTransactionFile(String file){
        ArrayList<Transaction> transactions = new ArrayList<>();
        ArrayList<TransactionType> transactionTypes = new ArrayList<>();

        int layer = 0;
        int element = 0;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
            int c;
            while((c = reader.read()) != -1) {
                char character = (char) c;

                switch (character){
                    case 'b':
                        character = (char) reader.read(); //leer la b, o <
                        if (character == '<'){
                            layer = readNumber(reader);
                        }else{
                            layer = 0;  //como no hay # es la layer 0
                        }
                        break;
                    case 'r' :
                        reader.read();
                        element = readNumber(reader);
                        transactionTypes.add(new TransactionType(Config.READ,element,0));
                        break;
                    case 'w':
                        int value = 0;
                        reader.read();
                        element = readNumber(reader);
                        value = readNumber(reader);
                        transactionTypes.add(new TransactionType(Config.WRITE,element,value));
                        break;
                    case 'c':
                        TransactionType [] ts = new TransactionType[transactionTypes.size()];
                        ts = transactionTypes.toArray(ts);
                        Transaction t = new Transaction(layer, ts);
                        transactions.add(t);
                        transactionTypes.clear();
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private static int readNumber(BufferedReader reader){
        StringBuilder num_string = new StringBuilder();
        char character;
        try {
            character = (char) reader.read();
            while(isNumber(character)){      //conseguir el #
                num_string.append(character);
                character = (char) reader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.valueOf(num_string.toString());
    }

    private static boolean isNumber(char c){
        return c >= '0' && c <= '9';
    }

}
