package server;

import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseCode;
import common.utility.Outputer;
import server.utility.RequestHandler;

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
    private int soTimeout;
    private DatagramSocket serverSocket;
    private RequestHandler requestHandler;
    Request userRequest = null;
    Response responseToUser = null;

    public Server(int port, int soTimeout, RequestHandler requestHandler) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.requestHandler = requestHandler;
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
            App.logger.info("Запуск сервера...");
            DatagramChannel channel = DatagramChannel.open();
            InetSocketAddress isa = new InetSocketAddress("localhost",port);
            channel.socket().bind(isa);
            App.logger.info("Сервер успешно запущен.");
            channel.configureBlocking(false);
            SelectionKey clientKey = channel.register(selector, SelectionKey.OP_READ);
            clientKey.attach(new Con());
            Outputer.println("Прослушивание порта '" + port + "'...");
            App.logger.info("Прослушивание порта '" + port + "'...");
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    selector.select();
                    Iterator selectedKeys = selector.selectedKeys().iterator();
                    while (selectedKeys.hasNext() && processingStatus) {
                        try {
                            SelectionKey key = (SelectionKey) selectedKeys.next();
                            selectedKeys.remove();

                            if (!key.isValid()) {
                                continue;
                            }

                            if (key.isReadable()) {
                                processingStatus = read(key);
                                key.interestOps(SelectionKey.OP_WRITE);
                            }else if (key.isWritable()){
                                write(key);
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        } catch (IOException e) {
                            if (userRequest == null) {
                                Outputer.printerror("Непредвиденный разрыв соединения с клиентом!");
                                App.logger.warn("Непредвиденный разрыв соединения с клиентом!");
                            } else {
                                Outputer.println("Клиент успешно отключен от сервера!");
                                App.logger.info("Клиент успешно отключен от сервера!");
                            }
                        }
                    }
                } catch (IOException e) {
                    Outputer.printerror("Произошла ошибка при работе с клиентами!");
                    App.logger.error("Произошла ошибка при работе с клиентами!");
                }
            }
            App.logger.info("Завершение работы сервера...");
            if (channel != null) channel.close();
            Outputer.println("Работа сервера успешно завершена.");
            App.logger.info("Работа сервера успешно завершена.");
        } catch (IOException e) {
            Outputer.printerror("Произошла ошибка при попытке использовать порт '" + port + "'!");
            App.logger.fatal("Произошла ошибка при попытке использовать порт '" + port + "'!");
            Outputer.printerror("Сервер не может быть запущен!");
            App.logger.fatal("Сервер не может быть запущен!");
        }
    }

    private boolean read(SelectionKey key) throws IOException {
        DatagramChannel chan = (DatagramChannel) key.channel();
        Outputer.println("Соединение с клиентом успешно установлено.");
        App.logger.info("Соединение с клиентом успешно установлено.");
        Con con = (Con) key.attachment();
        con.sa = chan.receive(con.req);
        String receivedData = new String(con.req.array(), "UTF-8").trim();
        userRequest = Request.outOfString(receivedData);
        if (con.req != null) {
            con.req = ByteBuffer.allocate( 65536);
        }
        App.logger.info("Запрос '" + userRequest.getCommandName() + "' успешно обработан.");
        responseToUser = requestHandler.handle(userRequest);
        con.resp = ByteBuffer.wrap(responseToUser.getBytes());
        return true;
    }

    private void write(SelectionKey key) throws IOException {
        DatagramChannel chan = (DatagramChannel)key.channel();
        Con con = (Con)key.attachment();
        chan.send(con.resp, con.sa);
    }
}