package client;

import client.utility.UserHandler;
import common.exceptions.ConnectionErrorException;
import common.exceptions.NotInDeclaredLimitsException;
import common.interaction.Request;
import common.interaction.Response;
import common.utility.Outputer;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Runs the client.
 */
public class Client {
    private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private UserHandler userHandler;
    private DatagramSocket clientSocket;
    private InetAddress IPAddress;

    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, UserHandler userHandler) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userHandler = userHandler;
    }

    /**
     * Begins client operation.
     */
    public void run() {
        try {
            boolean workingStatus = true;
            while (workingStatus) {
                try {
                    connectToServer();
                    workingStatus = communicateWithServer();
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        Outputer.printerror("Превышено количество попыток подключения!");
                        break;
                    }
                    reconnectionAttempts++;
                    Outputer.println("Попытка повторного соединения № " + reconnectionAttempts );
                }
            }
            if (clientSocket != null) clientSocket.close();
            Outputer.println("Работа клиента завершена.");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("Клиент не может быть запущен!");
        }
    }

    private void connectToServer() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            if (reconnectionAttempts >= 1) {
                Outputer.println("Повторное соединение с сервером...");
            }
            clientSocket = new DatagramSocket();
            clientSocket.setSoTimeout(3000);
            IPAddress = InetAddress.getByName(host);
            Outputer.println("Попытка соединения с сервером произведена.");
        } catch (SocketException e) {
            Outputer.printerror("Произошла ошибка при соединении с сервером!");
            throw new ConnectionErrorException();
        } catch (UnknownHostException e) {
            Outputer.printerror("Адрес хоста сервера введён некорректно!");
            throw new NotInDeclaredLimitsException();
        }
    }

    private boolean communicateWithServer() throws ConnectionErrorException {
        Request requestToServer = null;
        Response serverResponse = null;
        byte[] sendingDataBuffer = new byte[65536];
        byte[] receivingDataBuffer = new byte[65536];
        do {
            try {
                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseCode()) :
                        userHandler.handle(null);
                sendingDataBuffer = requestToServer.getBytes();
                DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, port);
                clientSocket.send(sendingPacket);
                DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                clientSocket.receive(receivingPacket);
                reconnectionAttempts = 0;
                String receivedData = new String(receivingPacket.getData(), 0, receivingPacket.getLength(), StandardCharsets.UTF_8);
                serverResponse = Response.outOfString(receivedData);
                Outputer.print(serverResponse.getResponseBody());
            } catch (IOException e) {
                if (requestToServer.getCommandName().equals("exit")) {
                    return false;
                }
                Outputer.printerror("Соединение с сервером разорвано!");
                throw new ConnectionErrorException();
            }
        } while (!requestToServer.getCommandName().equals("exit"));
        return false;
    }
}