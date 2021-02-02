/**
 * Class:           CoreNode
 * Description:     Contains the extended funcionalities of a CoreNode
 *
 * @extends Node
 * @authors: Matias Villarroel, Víctor Garrido
 */

package nodes;

import connection.CoreLayerClient;
import model.Msg;
import utilities.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class CoreNode extends Node{

    private ArrayList<CoreLayerClient> clients;
    private ArrayList<CoreLayerClient> server;
    private int port;
    private int contTransactions;

    public CoreNode(String id) {

        super(id);
        clients = new ArrayList<>();
        server = new ArrayList<>();
        contTransactions = 0;
    }

    /**
     * Starts the core server given the port
     * @param port where other CoreNodes will connect
     */
    public void startServer(int port){
        this.port = port;
        this.start();
    }

    /**
     * Starts incoming connections from other nodes in the core layer
     */
    @Override
    public void run(){
        try {
            System.out.println("Starting Core Server");
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("Accepting client");
                CoreLayerClient coreLayerClient = new CoreLayerClient(socket,this);
                coreLayerClient.start();
                clients.add(coreLayerClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects to a server
     * @param ports : Array of ports of al the other nodes
     */
    @Override
    public void connectToServer(int [] ports) {
        for (int i = 0; i < ports.length; i++){
            try {
                Socket socket = new Socket(Config.IP,ports[i]);
                CoreLayerClient coreLayerClient = new CoreLayerClient(socket,this);
                coreLayerClient.start();
                server.add(coreLayerClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends the updated values to the other nodes
     * @param msg containing the updates
     */
    public void broadcastUpdate(Msg msg){
        for(CoreLayerClient c: server){
            c.setNumACKS(server.size());
            c.sendMessage(msg);
        }
    }

    /**
     * Avoids connection errors waiting for everyone to be ready to connect
     */
    public void ready(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Press enter when everyone is ready");
        String text= scan.nextLine(); //wait
    }

    /**
     * Updates a given element from a client. If this is the X = MAX_TRANSACTIONS time it's updated, it propages it to the next layer
     * @param element_pos
     * @param value
     */
    @Override
    public void updateElement(int element_pos, int value){
        super.updateElement(element_pos,value);
        contTransactions++;
        System.out.println("CORE_NODE COUNTER: " + contTransactions );
        if(contTransactions >= Config.MAX_TRANSACTIONS){
            super.propagateUpdate();
            contTransactions = 0;
        }

    }
}
