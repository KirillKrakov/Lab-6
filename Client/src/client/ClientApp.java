package client;

import client.utility.ClientConsoleManager;
import common.exceptions.NotInDeclaredLimitsException;
import common.exceptions.WrongAmountOfElementsException;
import common.utility.Outputer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;

/**
 * Главный класс, запускающий приложение клиента
 */
public class ClientApp {
    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";

    private static final int RECONNECTION_TIMEOUT = 3 * 1000;
    private static final int MAX_RECONNECTION_ATTEMPTS = 2;

    private static String host;
    private static int port;

    /**
     * Метод, устанавливающий хост и порт для клиента из аргументов командной строки
     * @param hostAndPortArgs аргументы командной строки - хост и порт
     * @return boolean - условие установки хоста и порта
     */
    private static boolean initializeConnectionAddress(String[] hostAndPortArgs) {
        try {
            if (hostAndPortArgs.length != 2) throw new WrongAmountOfElementsException();
            host = hostAndPortArgs[0];
            port = Integer.parseInt(hostAndPortArgs[1]);
            if (port < 0) throw new NotInDeclaredLimitsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Outputer.println("При запуске клиента для введения своих хоста и порта используйте аргументы командной строки. " +
                    "Текущий хост = localhost. Текущий порт = 1821");
        } catch (NumberFormatException exception) {
            Outputer.printerror("Порт должен быть представлен числом!");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("Порт не может быть отрицательным!");
        }
        return false;
    }

    /**
     * Метод, запускающий работу приложения клиента
     * @param args
     */
    public static void main(String[] args) {
        try {
            if (!initializeConnectionAddress(args)) {
                host = "localhost";
                port = 1821;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            ClientConsoleManager clientConsoleManager = new ClientConsoleManager(reader);
            Client client = new Client(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS, clientConsoleManager);
            client.run();
            DatagramSocket s = new DatagramSocket(1821);
            reader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
            Outputer.printerror("Ошибка при работе с консолью!");
        }
    }
}
