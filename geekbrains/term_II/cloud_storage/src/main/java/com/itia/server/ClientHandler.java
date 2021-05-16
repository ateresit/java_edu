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
