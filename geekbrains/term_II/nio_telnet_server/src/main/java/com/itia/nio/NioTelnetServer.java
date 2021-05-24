package com.itia.nio;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class NioTelnetServer {
    // делаем help
    public static final String LS_COMMAND = "\tls\t\tview all files and directories\n";
    public static final String MKDIR_COMMAND = "\tmkdir\t\tcreate directory\n";
    public static final String CHANGE_NICKNAME = "\tnick\t\tchange nickname\n";
    public static final String CREATE_FILE_COMMAND = "\ttouch\t\tcreate new file into current directory\n";
    public static final String CHANGE_DIR_COMMAND = "\tcd\t\t change current directory\n";
    public static final String DELETE_FILE_COMMAND = "\trm\t\t remove file or directory\n";
    public static final String COPY_COMMAND = "\tcopy\t\t copying file\n";
    public static final String FILE_CONTENT_COMMAND = "\tcat\t\t show content of file\n";

    public static final int SERVER_PORT = 8787;
    private final ByteBuffer buffer = ByteBuffer.allocate(512);
    private static final String ROOT_DIR = "test_folder";
    private Path currentPath = Path.of(ROOT_DIR);
    private String nickName = "";
    private Map<SocketAddress, String> clients = new HashMap<>();
    private String fileSeparator = System.getProperty("file.separator"); // определяем разделитель для path из свойств ОС
    private String lineSeparator = System.lineSeparator(); // определяем разделитель для строк из свойств ОС

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
                sendMessage(CREATE_FILE_COMMAND, selector, client);
                sendMessage(CHANGE_DIR_COMMAND, selector, client);
                sendMessage(DELETE_FILE_COMMAND, selector, client);
                sendMessage(COPY_COMMAND, selector, client);
                sendMessage(FILE_CONTENT_COMMAND, selector, client);

            } else if ("ls".equals(command)) {
                sendMessage(getFileList().concat("\n"), selector, client);

            } else if ("exit".equals(command)) {
                System.out.println("Client logged out. IP: " + channel.getRemoteAddress());
                channel.close();
                return;

            } else if (command.startsWith("cd")) {
                changeCurrentDir(selector, command, client);

            } else if (command.startsWith("touch")) {
                creatingFile(selector, command, client);

            } else if (command.startsWith("mkdir")) {
                creatingDirectory(selector, command, client);

            } else if (command.startsWith("rm")) {
                deleteFileOrDirectory(selector, command, client);

            } else if (command.startsWith("copy")) {
                copyFile (selector, command, client);

            } else if (command.startsWith("cat")) {
                showFileContent(selector, command, client);

            } else if (command.startsWith("nick")) {
                nickName = command.split(" ")[1];
                clients.put(channel.getRemoteAddress(), nickName);

                System.out.println("Client " + channel.getRemoteAddress().toString() + " change name on " + nickName);

            }
        }

        // всегда выводим строку с ником
        sendNickName(channel, nickName);
    }

    private void showFileContent(Selector selector, String command, SocketAddress client) throws IOException {
        String fileName = command.split(" ")[1];

        String absoluteFilePath = currentPath + fileSeparator + fileName;
        File contentFile = new File(absoluteFilePath);

        Path contentFilePath = Paths.get(absoluteFilePath);

        if (contentFile.exists()) {
            try {
                String content = Files.readString(contentFilePath);
                System.out.println(content);
                sendMessage(content, selector, client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sendMessage("Error. File " + absoluteFilePath + " not found.\n", selector, client);
        }

    }

    private void copyFile(Selector selector, String command, SocketAddress client) throws IOException {
        String srcFileName = command.split(" ")[1];
        String destFileName = command.split(" ")[2];

        String absoluteSrcPath = currentPath + fileSeparator + srcFileName;
        File srcFile = new File(absoluteSrcPath);

        Path srcPath = Paths.get(absoluteSrcPath);
        Path destPath = Paths.get(currentPath + fileSeparator + destFileName);

        if (srcFile.exists()) {
            try {
                Files.copy(srcPath, destPath, REPLACE_EXISTING);
                sendMessage("File " + srcFileName + " copy to " + destFileName + " successfully.\n", selector, client);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sendMessage("Error. File " + absoluteSrcPath + " not found.\n", selector, client);
        }

    }

    private void creatingDirectory(Selector selector, String command, SocketAddress client) throws IOException {
        String createDirName = command.split(" ")[1];
        String absolutePathDirCreate = currentPath + fileSeparator + createDirName;

        File newDirectory = new File(absolutePathDirCreate);

        if (!newDirectory.exists()) {
            try {
                newDirectory.mkdir();
                sendMessage("Directory " + absolutePathDirCreate + " created.\n", selector, client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sendMessage("Error. Directory " + absolutePathDirCreate + " always exists.\n", selector, client);
        }

    }

    private void deleteFileOrDirectory(Selector selector, String command, SocketAddress client) throws IOException {
        String deleteFileOrDirName = command.split(" ")[1];
        String absoluteFilePathDel = currentPath + fileSeparator + deleteFileOrDirName;

        File delFile = new File(absoluteFilePathDel);

        // если путь указывает на папку, удаляем
        if (delFile.isDirectory()) {
            if (delFile.list().length == 0) { // если папка пустая
                delFile.delete();
                sendMessage("Directory " + absoluteFilePathDel + " deleted.\n", selector, client);
            } else {
                sendMessage("Error. Directory " + absoluteFilePathDel + " is not empty.\n", selector, client);
            }
        }

        // если путь указывает на файл, удаляем
        if (delFile.isFile()) {
            if (delFile.delete()) {
                sendMessage("File " + absoluteFilePathDel + " deleted.\n", selector, client);
            }
        } else {
            sendMessage("Error. File " + absoluteFilePathDel + " not exists.\n", selector, client);
        }

    }

    private void creatingFile(Selector selector, String command, SocketAddress client) throws IOException {
        String newFileName = command.split(" ")[1];
        String absoluteFilePath = currentPath + fileSeparator + newFileName;

        File newFile = new File(absoluteFilePath);

        if (newFile.createNewFile()) {
            sendMessage("File " + absoluteFilePath + " created.\n", selector, client);
        } else {
            sendMessage("Error. File " + absoluteFilePath + " exists.\n", selector, client);
        }

    }

    private void sendNickName(SocketChannel channel, String nickName) throws IOException {
        // замена пустоты на сетевой адрес
        if (nickName.isEmpty()) {
            nickName = clients.getOrDefault(channel.getRemoteAddress(), channel.getRemoteAddress().toString());
        }

        // выводим ~ если находимся в корне
        String currentPathString = currentPath.toString().replace(ROOT_DIR, "~");

        // формируем строку и выводим в канал
        channel.write(
                ByteBuffer.wrap(nickName.concat(">:").concat(currentPathString).concat("$ ").getBytes(StandardCharsets.UTF_8))
        );
    }

    private void changeCurrentDir(Selector selector, String command, SocketAddress client) throws IOException {
        if (!command.contains(" ")) {
            throw new IllegalArgumentException("Command must have space separator!");
        }

        // получаем имя целевой папки из командной строки
        String targetDir = command.split(" ")[1];

        // формируем путь от текущей папки
        Path targetPath = Path.of(String.valueOf(currentPath), targetDir);

        // если команда на смену директории вверх
        if ("..".equals(targetDir)) {
            targetPath = currentPath.getParent(); // поднимаемся вверх и получаем путь
        }

        // попытка выхода пути выше корня
        if (targetPath == null || !targetPath.startsWith(ROOT_DIR)) {
            sendMessage("Unreachable destination folder. You are in the root directory!", selector, client);
        } else {
            currentPath = targetPath;
        }

        // переход в корень
        if ("~".equals(targetDir)) {
            currentPath = Path.of(ROOT_DIR);
            // если папка существует - переходим в неё
        } else if (targetPath.toFile().exists()) {
            currentPath = targetPath;
            // нет такой папки, если проверку не прошли
        } else {
            sendMessage("Unreachable destination folder. Directory does not exist!", selector, client);
        }

    }

    private String getFileList() {
        return String.join(" ", new File(String.valueOf(currentPath)).list()); // test_folder - начальная папка
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

        // после подключения выводим строку с ником
        sendNickName(channel, "");

    }

    public static void main(String[] args) throws IOException {
        new NioTelnetServer();
    }
}
