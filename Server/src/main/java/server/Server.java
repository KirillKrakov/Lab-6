package server;

import common.communication.*;
import common.communication.request.*;
import common.utility.Outputer;
import server.utility.CollectionManager;
import server.utility.RequestManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * Runs the server.
 */
public class Server {
    private int port;
    private RequestManager requestManager;
    private CollectionManager collectionManager;
    Request userRequest = null;
    Response responseToUser = null;

    public Server(int port, RequestManager requestManager, CollectionManager collectionManager) {
        this.port = port;
        this.requestManager = requestManager;
        this.collectionManager = collectionManager;
    }
    class Con {
        ByteBuffer req;
        ByteBuffer resp;
        SocketAddress sa;

        public Con() {
            req = ByteBuffer.allocate(65536);
        }
    }
    /**
     * Begins server operation.
     */
    public void run() {
        try {
            Selector selector = Selector.open();
            ServerApp.logger.info("Запуск сервера...");
            DatagramChannel channel = DatagramChannel.open();
            InetSocketAddress isa = new InetSocketAddress("localhost",port);
            channel.socket().bind(isa);
            ServerApp.logger.info("Сервер успешно запущен.");
            channel.configureBlocking(false);
            SelectionKey clientKey = channel.register(selector, SelectionKey.OP_READ);
            clientKey.attach(new Con());
            Outputer.println("Прослушивание порта '" + port + "'...");
            ServerApp.logger.info("Прослушивание порта '" + port + "'...");
            while (true) {
                try {
                    while (true) {
                        if (System.in.available() != 0) {
                            communicateWithConsole();
                        }
                        selector.selectNow();
                        if (selector.selectedKeys() != null) {
                            break;
                        }
                    }
                    Set selectedKey = selector.selectedKeys();
                    Iterator selectedKeys = selectedKey.iterator();
                    while (selectedKeys.hasNext()) {
                        try {
                            SelectionKey key = (SelectionKey) selectedKeys.next();
                            selectedKeys.remove();
                            if (!key.isValid()) {
                                continue;
                            }
                            if (key.isReadable()) {
                                read(key);
                                key.interestOps(SelectionKey.OP_WRITE);
                            }else if (key.isWritable()){
                                write(key);
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        } catch (IOException e) {
                            if (userRequest == null) {
                                Outputer.printerror("Непредвиденный разрыв соединения с клиентом!");
                                ServerApp.logger.warn("Непредвиденный разрыв соединения с клиентом!");
                            } else {
                                Outputer.println("Клиент успешно отключен от сервера!");
                                ServerApp.logger.info("Клиент успешно отключен от сервера!");
                            }
                        }
                    }
                } catch (IOException e) {
                    Outputer.printerror("Произошла ошибка при работе с клиентами!");
                    ServerApp.logger.error("Произошла ошибка при работе с клиентами!");
                }
            }
        } catch (IOException e) {
            Outputer.printerror("Произошла ошибка при попытке использовать порт '" + port + "'!");
            ServerApp.logger.fatal("Произошла ошибка при попытке использовать порт '" + port + "'!");
            Outputer.printerror("Сервер не может быть запущен!");
            ServerApp.logger.fatal("Сервер не может быть запущен!");
        }
    }

    private void read(SelectionKey key) throws IOException {
        DatagramChannel chan = (DatagramChannel) key.channel();
        Outputer.println("Соединение с клиентом успешно установлено.");
        ServerApp.logger.info("Соединение с клиентом успешно установлено.");
        Con con = (Con) key.attachment();
        con.sa = chan.receive(con.req);
        String receivedData = new String(con.req.array(), "UTF-8").trim();
        userRequest = getUserRequest(receivedData);
        if (con.req != null) {
            con.req = ByteBuffer.allocate( 65536);
        }
        ServerApp.logger.info("Запрос '" + userRequest.getCommandName() + "' успешно обработан.");
        responseToUser = requestManager.handle(userRequest);
        con.resp = ByteBuffer.wrap(responseToUser.getBytes());
    }

    private void write(SelectionKey key) throws IOException {
        DatagramChannel chan = (DatagramChannel)key.channel();
        Con con = (Con)key.attachment();
        chan.send(con.resp, con.sa);
    }

    private void communicateWithConsole() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        if (input.equals("save")) {
            collectionManager.saveCollection();
            Outputer.println("Коллекция сохранена.");
        } else if (input.equals("server_exit")) {
            collectionManager.saveCollection();
            Outputer.println("Работа сервера успешно завершена.");
            System.exit(1);
        } else {
            Outputer.println("Команда не найдена. На сервере возможен ввод только команд save и server_exit");
        }
    }

    private Request getUserRequest(String arg) {
        String[] request = arg.split("~~~");
        Request userRequest;
        switch (request[0]) {
            case ("add"):
            case ("add_if_min"):
            case ("remove_lower"):
                userRequest = new WithLabWorkRequest();
                break;
            case ("update"):
                userRequest = new UpdateRequest();
                break;
            case ("remove_all_by_author"):
                userRequest = new WithAuthorRequest();
                break;
            case ("remove_by_id"):
            case ("execute_script"):
            case ("count_less_than_minimal_point"):
            case ("filter_contains_name"):
                userRequest = new WithStrArgRequest();
                break;
            default:
                userRequest = new OnlyCommandRequest();
        }
        return userRequest.getData(request);
    }
}