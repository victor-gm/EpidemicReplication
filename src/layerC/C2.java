/**
 * Class:           C2
 * Description:     Layer 1 node C2
 * @authors: Matias Villarroel, Víctor Garrido
 */

package layerC;

import nodes.LayerNode;
import nodes.Node;
import utilities.Config;

public class C2 {

    public static void main(String[] args){
        int [] ports = {Config.B1_LAYER_2_PORT};

        LayerNode layerNode = new LayerNode(Config.C2_ID);
        layerNode.startClientConnection(Config.C2_CLIENT_PORT);
        layerNode.startWebSocketConnection(Config.C2_WEBSOCKET_PORT);
        layerNode.connectToServer(ports);
    }

}
