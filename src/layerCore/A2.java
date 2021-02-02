/**
 * Class:           A2
 * Description:     Core node A2
 * @authors: Matias Villarroel, Víctor Garrido
 */

package layerCore;

import nodes.CoreNode;
import utilities.Config;

public class A2 {
    public static void main(String[] args){
        int [] ports = {Config.A1_CORE_PORT, Config.A3_CORE_PORT};

        CoreNode coreNode = new CoreNode(Config.A2_ID);
        coreNode.startClientConnection(Config.A2_CLIENT_PORT);
        coreNode.startWebSocketConnection(Config.A2_WEBSOCKET_PORT);
        coreNode.startNextLayerServer(Config.A2_LAYER_1_PORT);
        coreNode.startServer(Config.A2_CORE_PORT);
        coreNode.ready();
        coreNode.connectToServer(ports);
    }
}
