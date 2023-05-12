package server;

import common.communication.Request;
import common.communication.Response;
import common.utility.Outputer;
import server.utility.RequestManager;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * Runs the server.
 */
public class Server {
    private int port;
    private DatagramSocket serverSocket;
    private RequestManager requestManager;
    Request userRequest = null;
    Response responseToUser = null;

    public Server(int port, RequestManager requestManager) {
        this.port = port;
        this.requestManager = requestManager;
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
                    selector.select();
                    Iterator selectedKeys = selector.selectedKeys().iterator();
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
        userRequest = Request.outOfString(receivedData);
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
}