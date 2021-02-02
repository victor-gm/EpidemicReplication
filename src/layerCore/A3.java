/**
 * Class:           A3
 * Description:     Core node A3
 * @authors: Matias Villarroel, Víctor Garrido
 */


package layerCore;

import nodes.CoreNode;
import utilities.Config;

public class A3 {

    public static void main(String[] args){
        int [] ports = {Config.A1_CORE_PORT, Config.A2_CORE_PORT};

        CoreNode coreNode = new CoreNode(Config.A3_ID);
        coreNode.startClientConnection(Config.A3_CLIENT_PORT);
        coreNode.startWebSocketConnection(Config.A3_WEBSOCKET_PORT);
        coreNode.startNextLayerServer(Config.A3_LAYER_1_PORT);
        coreNode.startServer(Config.A3_CORE_PORT);
        coreNode.ready();
        coreNode.connectToServer(ports);

    }
}
