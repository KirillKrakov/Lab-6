package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.commands.*;
import server.utility.*;

import static java.lang.System.getenv;

public class App {
    public static final int PORT = 1821;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static final String ENV_VARIABLE = getenv("lab5");
    public static Logger logger = LogManager.getLogger(App.class.getCanonicalName());

    public static void main(String[] args) {
        CollectionFileManager collectionFileManager = new CollectionFileManager(ENV_VARIABLE);
        CollectionManager collectionManager = new CollectionManager(collectionFileManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ClearCommand(collectionManager),
                new ExitCommand(),
                new ExecuteScriptCommand(),
                new AddIfMinCommand(collectionManager),
                new RemoveLowerCommand(collectionManager),
                new HistoryCommand(),
                new RemoveAllByAuthorCommand(collectionManager),
                new CountLessThanMinimalPointCommand(collectionManager),
                new FilterContainsNameCommand(collectionManager)
        );
        RequestHandler requestHandler = new RequestHandler(commandManager);
        ConsoleManager consoleManager = new ConsoleManager(collectionManager);
        Thread myThread = new Thread(consoleManager);
        myThread.start();
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
    }
}
