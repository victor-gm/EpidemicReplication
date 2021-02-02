/**
 * Class:           B1
 * Description:     Layer 1 node B1
 * @authors: Matias Villarroel, Víctor Garrido
 */

package layerB;

import nodes.LayerNode;
import utilities.Config;

public class B1 {

    public static void main(String[] args){
        int [] ports = {Config.A2_LAYER_1_PORT};

        LayerNode layerNode = new LayerNode(Config.B1_ID);
        layerNode.startClientConnection(Config.B1_CLIENT_PORT);
        layerNode.startWebSocketConnection(Config.B1_WEBSOCKET_PORT);
        layerNode.startNextLayerServer(Config.B1_LAYER_2_PORT);
        layerNode.startPropagationTimer();
        layerNode.connectToServer(ports);

    }

}
