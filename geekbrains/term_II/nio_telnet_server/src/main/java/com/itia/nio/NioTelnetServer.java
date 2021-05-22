package com.itia.nio;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NioTelnetServer {
    // делаем help
    public static final String LS_COMMAND = "\tls\t\tview all files and directories\n";
    public static final String MKDIR_COMMAND = "\tmkdir\t\tcreate directory\n";
    public static final String CHANGE_NICKNAME = "\tnick\t\tchange nickname\n";

    public static final int SERVER_PORT = 8787;
    private final ByteBuffer buffer = ByteBuffer.allocate(512);

    public NioTelnetServer() throws IOException {
        // откроем серверный сокетный канал
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(SERVER_PORT)); // т.к. работает InetSocketAddress, нельзя просто прописать порт
        server.configureBlocking(false); // включаем несинхронный режим работы, к нам могут подключаться. первоначально, передача данных синхронная как в io
        //OP_ACCEPT, OP_READ, OP_WRITE - события участвуют в регистрации селектора на сервере

        // откроем селектор
        Selector selector = Selector.open();

        server.register(selector, SelectionKey.OP_ACCEPT); // сервер слушает на присоединение
        System.out.println("Server started");

        while (server.isOpen()) {
            selector.select();

            var selectionKeys = selector.selectedKeys();
            var iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                var key = iterator.next();
                if (key.isAcceptable()) {
                    handleAccept(key, selector);
                } else if (key.isReadable()) {
                    handleRead(key, selector);
                }
                iterator.remove(); // нужно вернуть ключик! иначе не заработает!
            }
        }

    }

    private void handleRead(SelectionKey key, Selector selector) throws IOException {
        SocketChannel channel = ((SocketChannel) key.channel()); // канал теперь какой-то есть
        SocketAddress client = channel.getRemoteAddress(); //для идентификации клиента. клиент останется в памяти
        int readBytes = channel.read(buffer); // читаем буфер
        if (readBytes < 0) {
            channel.close();
            return;
        } else if (readBytes == 0) {
            return;
        }

        buffer.flip(); // обнулили позицию после записи в буфер

        StringBuilder sb = new StringBuilder();
        while (buffer.hasRemaining()) {
            sb.append((char) buffer.get());
        }

        buffer.clear();

        // TODO
        // touch [filename] - создание файла
        // mkdir [dirname] - создание директории
        // cd [path] - перемещение по каталогу (.. | ~ )
        // rm [filename | dirname] - удаление файла или папки
        // copy [src] [target] - копирование файла или папки
        // cat [filename] - просмотр содержимого
        // вывод nickname в начале строки

        if (key.isValid()) {
            //  требуется замена символов в зависимости от ОС (в macOS представленный вариант работает), чтобы команда была "чистая". если убрать замену - не работает!
            String command = sb
                    .toString()
                    .replace("\n", "")
                    .replace("\r", "");

            if ("--help".equals(command)) { // сравниваем именно так, чтобы не было ошибки сравнения и не валилось исключение NullPointerException
                sendMessage(LS_COMMAND, selector, client);
                sendMessage(MKDIR_COMMAND, selector, client);
                sendMessage(CHANGE_NICKNAME, selector, client);
            } else if ("ls".equals(command)) {
                sendMessage(getFileList().concat("\n"), selector, client);

            } else if ("exit".equals(command)) {
                System.out.println("Client logged out. IP: " + channel.getRemoteAddress());
                channel.close();
                return;
            }

        }
    }

    private String getFileList() {
        return String.join(" ", new File("test_folder").list()); // test_folder - начальная папка
    }

    private void sendMessage(String message, Selector selector, SocketAddress client) throws IOException {
        // в цикле проходим по ключам селектора
        for (SelectionKey key : selector.keys()) {
            if (key.isValid() && key.channel() instanceof SocketChannel) {
                if (((SocketChannel) key.channel()).getRemoteAddress().equals(client) ); // нашли конкретного клиента, сообщение без широковещательного сообщения
                    ((SocketChannel) key.channel())
                            .write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
            }
        }
    }

    private void handleAccept(SelectionKey key, Selector selector) throws IOException {
        SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();

        //канал устанавливаем в несинхронизированный режим
        channel.configureBlocking(false);
        System.out.println("Client connected. IP: " + channel.getRemoteAddress());

        channel.register(selector, SelectionKey.OP_READ, "some_attach");
        channel.write(ByteBuffer.wrap("Hello user!\n".getBytes(StandardCharsets.UTF_8))); // приветствие подключившегося
        channel.write(ByteBuffer.wrap("Enter --help for support info.\n".getBytes(StandardCharsets.UTF_8)));

    }

    public static void main(String[] args) throws IOException {
        new NioTelnetServer();
    }
}
