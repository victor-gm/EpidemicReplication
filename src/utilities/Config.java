/**
 * Class:           Config
 * Description:     Contains all the configurations needed to run the system
 *                  Ports : Ports that all servers hold
 *                  IP    : IP adress of the system
 *                  TYPE  : Type of Transaction: READ, WRITE, CLOSE
 * @authors: Matias Villarroel, Víctor Garrido
 */

package utilities;

public class Config {

    /************** Ports *************/

    //Ports for clients to connect
    public static final int A1_CLIENT_PORT = 8080;
    public static final int A2_CLIENT_PORT = 8081;
    public static final int A3_CLIENT_PORT = 8082;
    public static final int B1_CLIENT_PORT = 8083;
    public static final int B2_CLIENT_PORT = 8084;
    public static final int C1_CLIENT_PORT = 8085;
    public static final int C2_CLIENT_PORT = 8086;

    //Ports for members of core layer to connect
    public static final int A1_CORE_PORT = 8087;
    public static final int A2_CORE_PORT = 8088;
    public static final int A3_CORE_PORT = 8089;

    //Ports for members of layer 1 layer to connect to core nodes
    public static final int A2_LAYER_1_PORT = 8090;
    public static final int A3_LAYER_1_PORT = 8091;

    //Ports for members of layer 2 layer to connect layer 1 nodes
    public static final int B1_LAYER_2_PORT = 8092;
    public static final int B2_LAYER_2_PORT = 8093;

    //Ports for the monitoring websockets to connect
    public static final int A1_WEBSOCKET_PORT = 8094;
    public static final int A2_WEBSOCKET_PORT = 8095;
    public static final int A3_WEBSOCKET_PORT = 8096;
    public static final int B1_WEBSOCKET_PORT = 8097;
    public static final int B2_WEBSOCKET_PORT = 8098;
    public static final int C1_WEBSOCKET_PORT = 8099;
    public static final int C2_WEBSOCKET_PORT = 8100;

    //Constants
    public static final String IP = "localhost";

    //Types of transactions
    public static final String READ = "r";
    public static final String WRITE = "w";
    public static final String CLOSE = "c";
    public static final String ACK = "ack";

    //Logs
    public static final String PATH = "logs/";
    public static final String EXTENSION = ".log";

    //Max Values
    public static final int MAX_SIZE = 10;
    public static final int MAX_TRANSACTIONS = 10;
    public static final int MAX_TIME = 10000;

    //Node IDs
    public static final String A1_ID = "A1";
    public static final String A2_ID = "A2";
    public static final String A3_ID = "A3";
    public static final String B1_ID = "B1";
    public static final String B2_ID = "B2";
    public static final String C1_ID = "C1";
    public static final String C2_ID = "C2";

}
