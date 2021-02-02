/**
 * Class:           LayerNode
 * Description:     Contains the extended funcionalities of a LayerNode
 *
 * @extends Node
 * @authors: Matias Villarroel, Víctor Garrido
 */

package nodes;

import layerB.PropagateTimer;
import model.Msg;
import utilities.Config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LayerNode extends Node {


    public LayerNode(String id){
        super(id);
    }

    @Override
    public void connectToServer(int[] ports) {
        try {
            Socket socket = new Socket(Config.IP,ports[0]);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            while (true){
                Msg msg = (Msg) ois.readObject();
                if(msg.getType().equals(Config.WRITE)){
                    super.updateElement(msg.getElement(), msg.getValue());
                }else{
                    System.out.println("LAYER_NODE_MESSAGE: " + msg.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void startPropagationTimer(){
        PropagateTimer propagateTimer = new PropagateTimer(this);
        propagateTimer.start();
    }

}
