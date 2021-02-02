package layerB;

import nodes.Node;
import utilities.Config;

public class PropagateTimer extends Thread {

    private Node node;

    public PropagateTimer(Node node){
        this.node = node;
    }

    @Override
    public void run(){
        while (true){
            node.propagateUpdate();
            System.out.println("PROPAGATE_TIMER: propagating to next layer");
            try {
                sleep(Config.MAX_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
