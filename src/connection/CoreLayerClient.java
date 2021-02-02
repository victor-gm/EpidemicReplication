package connection;

import model.Msg;
import nodes.CoreNode;
import utilities.Config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class CoreLayerClient extends Thread {

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private CoreNode coreNode;
    private ArrayList<Msg> msgList;
    private boolean waitACKs = false;
    private int numACKS = 0;
    private int contACKs = 0;

    public CoreLayerClient(Socket socket, CoreNode coreNde){
        this.coreNode = coreNde;
        msgList = new ArrayList<>();
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while (true){
            try {
                Msg msg = (Msg) ois.readObject();
                if(waitACKs && !msg.getType().equals(Config.ACK)){
                    System.out.println("CORE_LAYER_CLIENT_ACKs");
                    msgList.add(msg);
                }else if(waitACKs && msg.getType().equals(Config.ACK)){
                    contACKs++;
                    System.out.println("CORE_LAYER_CLIENT_ACKs ++");
                    if(numACKS >= contACKs){
                        waitACKs = false;
                        System.out.println("ALL ACKs received");
                        coreNode.setWaitACKs(false);
                        for (Msg m : msgList){
                            if(m.getType().equals(Config.WRITE)){
                                coreNode.updateElement(msg.getElement(),msg.getValue());
                            }else{
                                System.out.println("CORE_LAYER_CLIENT: msg " + msg.toString());
                            }
                        }
                        msgList.clear();
                    }
                }
                else if (msg.getType().equals(Config.WRITE)){
                    coreNode.updateElement(msg.getElement(),msg.getValue());
                    sendMessage(new Msg(Config.ACK, -1, -1));
                }else{
                    System.out.println("CORE_LAYER_CLIENT: msg " + msg.toString());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(Msg message){
        try {
            oos.writeObject(message);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void setNumACKS(int acks){
        waitACKs = true;
        contACKs = 0;
        this.numACKS = acks;
    }

}
