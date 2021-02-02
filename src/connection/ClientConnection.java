/**
 * Class:           ClientConnection
 * Description:     Allows clients to connect to a node
 * Functionality :
 *                  1. Waits for a client to connect
 *                  2. Waits for a client to send an object, int his case, a transaction
 *                  3. Reads the type of transaction
 *                     a. Read : Sends back the requested data
 *                     b. Write: 1. Updates the requested data locally in the node
 *                               2. Broadcasts the update to other members of the layer
 * @authors: Matias Villarroel, Víctor Garrido
 */


package connection;

import utilities.Config;
import model.Msg;
import nodes.CoreNode;
import nodes.Node;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnection extends Thread{

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Node node;
    private int port;

   public ClientConnection(int port, Node node){
       this.node = node;
       this.port = port;
    }

    @Override
    public void run(){

       boolean close;

        try {
            //Wait for client to connect
            ServerSocket serverSocket = new ServerSocket(port);

            while (true){
                Socket socket = serverSocket.accept();
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());

                close = false;
                while (!close){
                    Msg msg = (Msg) ois.readObject();
                    if (msg.getType().equals(Config.READ)) {
                        oos.writeObject(new Msg(Config.READ, msg.getElement(), node.getElementValue(msg.getElement())));
                    } else if (msg.getType().equals(Config.WRITE) && (node instanceof CoreNode)) {
                        node.updateElement(msg.getElement(), msg.getValue());
                        Msg m = new Msg(Config.WRITE,msg.getElement(),msg.getValue());
                        System.out.println("CLIENT_SERVER_BROADCAST: " + m.toString());
                        ((CoreNode) node).broadcastUpdate(m);
                        node.setWaitACKs(true);
                        while(node.isWaitACKs()){
                            try {
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        oos.writeObject(new Msg(Config.ACK, -1, -1));
                    } else {
                        System.out.println("Msg received: " + msg.toString());
                        close = true;
                        socket.close();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
