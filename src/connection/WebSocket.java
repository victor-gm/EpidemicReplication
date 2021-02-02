package connection;

import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class WebSocket extends WebSocketServer {

    public WebSocket(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(org.java_websocket.WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("new connection to " + webSocket.getRemoteSocketAddress());
    }

    @Override
    public void onClose(org.java_websocket.WebSocket webSocket, int code, String reason, boolean b) {
        System.out.println("closed " + webSocket.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(org.java_websocket.WebSocket webSocket, String s) {

    }

    @Override
    public void onError(org.java_websocket.WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {
        System.out.println("Web Socket started successfully");
    }

}
