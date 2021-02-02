/**
 * Class:           NextLayerServer
 * Description:     Allows members of the next layer to connect
 * @authors: Matias Villarroel, Víctor Garrido
 */

package connection;

import model.Msg;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NextLayerServer extends Thread {

    private ServerSocket serverSocket;
    private ArrayList<NextLayerClient> clients;

    public NextLayerServer(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Next Layer Server is up");
            clients = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                Socket socket = serverSocket.accept();
                NextLayerClient nextLayerClient = new NextLayerClient(socket);
                clients.add(nextLayerClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends an update to the nodes on the next layer
     * @param msg
     */
    public void propagateUpdates(Msg msg){
        System.out.println("NEXT_LAYER_SERVER client list size: " + clients.size());
        for (NextLayerClient nlc: clients){
            System.out.println("NEXT_LAYER_SERVER sending msg ");
            nlc.sendMessage(msg);
        }
    }
}
