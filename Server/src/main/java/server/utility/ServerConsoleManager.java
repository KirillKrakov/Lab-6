package server.utility;

import java.util.Scanner;

/**
 * Класс, управляющий чтением введённых в консоль команд
 */
public class ServerConsoleManager implements Runnable {
    private CollectionManager collectionManager;
    private Scanner scanner;

    public ServerConsoleManager(CollectionManager collectionManager) {
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
                    System.out.println("Коллекция сохранена.");
                }else
                if (input.equals("server_exit"))
                {
                    collectionManager.saveCollection();
                    System.out.println("Работа сервера успешно завершена.");
                    System.exit(1);
                }
            }
        }
    }
}
