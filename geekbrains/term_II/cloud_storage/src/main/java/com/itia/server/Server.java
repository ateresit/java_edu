package com.itia.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int SERVER_PORT = 9977;
    private static final int THREAD_QTY = 4;

    public Server() {
        ExecutorService service = Executors.newFixedThreadPool(THREAD_QTY);

        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started at port: " + SERVER_PORT);

            while (true){
                Socket socket = server.accept();
                service.execute(new ClientHandler(socket));
                System.out.println("Client connected at port: " + socket.getInetAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
