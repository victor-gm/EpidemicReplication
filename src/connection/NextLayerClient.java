/**
 * Class:           NextLayerClient
 * Description:     Allows to send updates to the nodes on the next layer
 * @authors: Matias Villarroel, Víctor Garrido
 */
package connection;

import model.Msg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NextLayerClient extends Thread {

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public NextLayerClient(Socket socket){
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Msg msg){
        try {
            oos.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
