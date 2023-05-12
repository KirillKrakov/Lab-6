package server.utility;

import java.util.Scanner;

/**
 * Класс, управляющий чтением введённых в консоль команд
 */
public class ConsoleManager implements Runnable {
    private CommandManager commandManager;
    private CollectionManager collectionManager;
    private Scanner scanner;

    public ConsoleManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.scanner = new Scanner(System.in);
    }

    public void run()
    {
        while(true)
        {
            if (scanner.hasNext())
            {
                String input = scanner.nextLine();
                if (input.equals("save"))
                {
                    collectionManager.saveCollection();
                    System.out.println("Collection saved");
                }else
                if (input.equals("server_exit"))
                {
                    collectionManager.saveCollection();
                    System.out.println("Server stopping");
                    System.exit(1);
                }
            }
        }
    }
}
