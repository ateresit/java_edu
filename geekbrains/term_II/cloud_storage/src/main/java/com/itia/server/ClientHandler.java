package com.itia.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private static final String SERVER_FOLDER = "test_folder/server/";

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream())
        ) {
            while (true){
                String command = in.readUTF();
                // инициация и запись файла на сервер
                if ("upload".equals(command)){
                    uploading(out, in);
                }
                // инициализация и выгрузка файла с сервера
                if ("download".equals(command)){
                    // TODO: 13.05.2021 downloading
                    downloading (out, in);
                }

                // закрытие соединения по запросу от клиента
                if ("exit".equals(command)){
                    out.writeUTF("DONE");
                    disconnectClient();
                    break;
                }

                System.out.println(command);
                out.writeUTF(command);
            }
        } catch (SocketException socketException){
            System.out.printf("Client %s disconnected\n", socket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloading(DataOutputStream out, DataInputStream in) throws IOException {
        try {
            File file = new File(SERVER_FOLDER + in.readUTF());
            if (!file.exists()) {
                out.writeUTF("file_not_found"); // сообщение клиенту, что запрашиваемого файла нет на сервере
                throw new FileNotFoundException();
            }

            // файл найден, можно делать передачу клиенту
            out.writeUTF("file_found");
            long fileLength = file.length();

            FileInputStream fis = new FileInputStream(file);
            out.writeLong(fileLength);
            // делаем буфер
            int read = 0;
            byte[] buffer = new byte[8 * 1024];

            while ((read = fis.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush(); // очистка

            // отправляем статус клиенту
            while (true){
                String downloadResult = in.readUTF();
                if ("file_received".equals(downloadResult)) {
                    out.writeUTF("OK");
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            out.writeUTF("WRONG");
            System.err.println("File not found!");
        }
    }

    private void uploading(DataOutputStream out, DataInputStream in) throws IOException {
        try {
            File file = new File(SERVER_FOLDER + in.readUTF()); // read file
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            long fileSize = in.readLong();
            byte[] buffer = new byte[8 * 1024];

 //           for (int i = 0; i < ((fileSize + (buffer.length - 1)) / buffer.length); i++) {
            for (int i = 0; i < (fileSize + (8 * 1024 - 1)) / (8 * 1024); i++) {
                int read = in.read(buffer);
                fos.write(buffer, 0, read);
            }
            fos.close();
            out.writeUTF("OK");
        } catch (IOException e) {
            out.writeUTF("WRONG");
        }
    }

    private void disconnectClient() throws IOException {
        socket.close();
        System.out.printf("Client %s disconnected correctly\n", socket.getInetAddress());
    }
}
