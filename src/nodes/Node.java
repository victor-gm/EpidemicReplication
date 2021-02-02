/**
 * Class:           Node
 * Description:     Contains the main functionalities of all nodes
 * @authors: Matias Villarroel, Víctor Garrido
 */

package nodes;

import connection.ClientConnection;
import connection.NextLayerServer;
import connection.WebSocket;
import model.Msg;
import utilities.Config;
import org.java_websocket.server.WebSocketServer;
import utilities.Writer;

import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static utilities.Config.MAX_SIZE;

public abstract class Node extends Thread{

    protected int[] elements;                               // All the elements contained in the node
    protected HashMap<Integer, Integer> updatedElements;   // Just the elements that need to be updates
    protected WebSocketServer webSocketServer;
    private String id;
    protected NextLayerServer nextLayerServer;

    private boolean waitACKs;

    Node(String id){
        this.id = id;
        elements = new int [MAX_SIZE];
        for (int i = 0; i < MAX_SIZE; i++){
            elements[i] = 0;
        }
        updatedElements = new HashMap<>();
        waitACKs = false;
    }

    /**
     * Updates the value of a given element
     * @param element_pos
     * @param value
     */
    public synchronized void updateElement(int element_pos, int value){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (element_pos >= MAX_SIZE)
        {
            Writer.writeToFile(id, timestamp + "-- Element " + element_pos + " is out of bounds. It will be given the biggest position possible\n"); //if the value is bigger, modify the last value of the array
            element_pos = MAX_SIZE -1;
        }
        if (element_pos < 0)
        {
            Writer.writeToFile(id, timestamp + "-- Element " + element_pos + " is out of bounds. It will be given the smallest position possible\n"); //if the value is bigger, modify the last value of the array
            element_pos = 0; //if the value is smaller, modify the first value of the array
        }
        elements[element_pos] = value;

        Integer integer = updatedElements.replace(element_pos,value);
        if (integer == null) updatedElements.put(element_pos,value);
        webSocketServer.broadcast(new Msg(id,element_pos,elements[element_pos]).toJson());
        Writer.writeToFile(id, timestamp + "-- Element " + element_pos + " was updated to: " + value + "\n");
    }

    /**
     * Returns the value of a given element
     * @param element_pos
     * @return the value of the element
     */
    public synchronized int getElementValue(int element_pos){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (element_pos >= MAX_SIZE)
        {
            Writer.writeToFile(id, timestamp + "-- Reading Element " + element_pos + " is out of bounds. It will be given the biggest position possible\n"); //if the value is bigger, modify the last value of the array
            element_pos = MAX_SIZE -1;
        }
        if (element_pos < 0)
        {
            Writer.writeToFile(id, timestamp + "-- Reading Element " + element_pos + " is out of bounds. It will be given the smallest position possible\n"); //if the value is bigger, modify the last value of the array
            element_pos = 0; //if the value is smaller, modify the first value of the array
        }
        return elements[element_pos];
    }

    /**
     * Starts incomming connections from clients that want to read or write data
     * @param port that this has been given to clients to connect
     */
    public void startClientConnection(int port){
        ClientConnection cc = new ClientConnection(port,this);
        cc.start();
    }

    /**
     * Starts a connection with a websocket
     * @param websocketPort
     */
    public void startWebSocketConnection(int websocketPort){
        webSocketServer = new WebSocket(new InetSocketAddress(Config.IP,websocketPort));
        Thread thread = new Thread(webSocketServer);
        thread.start();
    }

    /**
     * Starts incoming connections from members of the next layer
     * @param port that has been given to next layers members to connect
     */
    public void startNextLayerServer(int port){
        nextLayerServer = new NextLayerServer(port);
        nextLayerServer.start();
    }

    /**
     * Obtains all the updated values and asks netxLayerServer to propagate them
     */
    public synchronized void propagateUpdate(){
        System.out.println("PROPAGATING");

        if(nextLayerServer != null){
            System.out.println("CORE_NODE INSIDE PROP and list: " + updatedElements.size());

            for(Map.Entry<Integer, Integer> entry : updatedElements.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println("Sending E: " + key + " - V: " + value + " e[i]: " + elements[key]);
                nextLayerServer.propagateUpdates(new Msg(Config.WRITE,key,value));
            }
            updatedElements.clear();
        }else{
            System.out.println("Next Layer server is not up");
        }
    }

    public abstract void connectToServer(int [] ports);

    public boolean isWaitACKs() {
        return waitACKs;
    }

    public void setWaitACKs(boolean waitACKs) {
        this.waitACKs = waitACKs;
    }

}
