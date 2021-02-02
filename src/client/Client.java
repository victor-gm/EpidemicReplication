/**
 * Class:           Client
 * Description:     Manages everything related to the client
 * Functionality :
 *                  1. Reads and parses the transactions files
 *                  2. Connects to a random member of the layer that requires the transaction
 *                  3. Sends the transaction to the member of the layer
 * @authors: Matias Villarroel, Víctor Garrido
 */

package client;

import model.Msg;
import model.Transaction;
import model.TransactionType;
import utilities.Config;
import utilities.Parser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Client {

    private static final int [] CORE_PORTS = {Config.A1_CLIENT_PORT, Config.A2_CLIENT_PORT, Config.A3_CLIENT_PORT};
    private static final int [] LAYER_1_PORTS = {Config.B1_CLIENT_PORT,Config.B2_CLIENT_PORT};
    private static final int [] LAYER_2_PORTS = {Config.C1_CLIENT_PORT,Config.C2_CLIENT_PORT};
    private static final String IP = "localhost";

    private ArrayList<Transaction> transactions;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket socket;

    /**
     * Reads and parses the file
     * @param file
     */
    public void readFile(String file){
        transactions = Parser.parseTransactionFile(file);
        System.out.println("READING FILE: " + file);
        for (Transaction t: transactions){
            System.out.println(t.toString());
        }
    }

    /**
     * Connects to a member of the layer
     * @param layer
     */
    public void connect(int layer){
        try {
            int port = getPort(layer);
            socket = new Socket(IP, port);
            System.out.println("connected to port: " + port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * For every transaction, connects to its layer member and sends it
     */
    public void doTransactions(){
        try {
            for (Transaction t: transactions){
                connect(t.getLayer());  /** connect to a port**/

                for (TransactionType tt: t.getTransactions()){
                    if (tt.getType().equals(Config.READ)) {
                        oos.writeObject(new Msg(tt.getType(), tt.getElement(), tt.getValue()));
                        Msg msg = (Msg) ois.readObject();
                        System.out.println("The element: " + tt.getElement() + " has a value of: " + msg.getValue());

                    } else {
                        oos.writeObject(new Msg(tt.getType(), tt.getElement(), tt.getValue()));
                        Msg msg = (Msg) ois.readObject();
                        System.out.println("RECEIVED :" + msg.getType() +" from server");
                    }
                }

                oos.writeObject(new Msg(Config.CLOSE,-1,-1)); //end connection
                socket.close(); /**close the socket **/
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * For a given layer, gets a random members port
     * @param layer
     * @return the random port of the member that will connect
     */
    private int getPort(int layer){
        Random rand = new Random();
        if (layer == 0) return CORE_PORTS[rand.nextInt(3)];
        else if(layer == 1) return LAYER_1_PORTS[rand.nextInt(2)];
        else if(layer == 2) return LAYER_2_PORTS[rand.nextInt(2)];
        else return CORE_PORTS[0];
    }

}
