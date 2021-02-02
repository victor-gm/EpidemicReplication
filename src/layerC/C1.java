/**
 * Class:           C1
 * Description:     Layer 2 node C1
 * @authors: Matias Villarroel, Víctor Garrido
 */
package layerC;

import nodes.LayerNode;
import utilities.Config;

public class C1 {

    public static void main(String[] args){
        int [] ports = {Config.B1_LAYER_2_PORT};

        LayerNode layerNode = new LayerNode(Config.C1_ID);
        layerNode.startClientConnection(Config.C1_CLIENT_PORT);
        layerNode.startWebSocketConnection(Config.C1_WEBSOCKET_PORT);
        layerNode.connectToServer(ports);
    }
}
