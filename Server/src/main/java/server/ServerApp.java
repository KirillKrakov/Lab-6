package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.utility.*;

import static java.lang.System.getenv;

public class ServerApp {
    public static final int PORT = 1821;
    public static final String ENV_VARIABLE = getenv("lab5");
    public static Logger logger = LogManager.getLogger(ServerApp.class.getCanonicalName());

    public static void main(String[] args) {
        FileManager fileManager = new FileManager(ENV_VARIABLE);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(collectionManager);
        RequestManager requestManager = new RequestManager(commandManager);
        ServerConsoleManager serverConsoleManager = new ServerConsoleManager(collectionManager);
        Thread myThread = new Thread(serverConsoleManager);
        myThread.start();
        Server server = new Server(PORT, requestManager);
        server.run();
    }
}
