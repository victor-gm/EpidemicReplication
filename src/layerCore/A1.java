/**
 * Class:           A1
 * Description:     Core node A1
 * @authors: Matias Villarroel, Víctor Garrido
 */

package layerCore;

import nodes.CoreNode;
import utilities.Config;

public class A1 {
    public static void main(String[] args){
        int [] ports = {Config.A2_CORE_PORT, Config.A3_CORE_PORT};

        CoreNode coreNode = new CoreNode(Config.A1_ID);
        coreNode.startClientConnection(Config.A1_CLIENT_PORT);
        coreNode.startWebSocketConnection(Config.A1_WEBSOCKET_PORT);
        coreNode.startServer(Config.A1_CORE_PORT);
        coreNode.ready();
        coreNode.connectToServer(ports);
    }
}
