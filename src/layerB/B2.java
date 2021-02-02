/**
 * Class:           B2
 * Description:     Layer 1 node B1
 * @authors: Matias Villarroel, Víctor Garrido
 */
package layerB;

import nodes.LayerNode;
import utilities.Config;

public class B2 {

    public static void main(String[] args){
        int [] ports = {Config.A3_LAYER_1_PORT};

        LayerNode layerNode = new LayerNode(Config.B2_ID);
        layerNode.startClientConnection(Config.B2_CLIENT_PORT);
        layerNode.startWebSocketConnection(Config.B2_WEBSOCKET_PORT);
        layerNode.connectToServer(ports);
        layerNode.startNextLayerServer(Config.B2_LAYER_2_PORT);
        layerNode.startPropagationTimer();
    }

}
