package ru.geekbrains;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static ru.geekbrains.Config.PORT;

public class WebServer {

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started!");
            RequestParser requestParser = new RequestParser();

            while(true) {
                Socket socket =serverSocket.accept();
                System.out.println("New client connected!");

                new Thread(new RequestHandler(new SocketService(socket), requestParser)).start();
            }
        } catch(IOException e) {
                e.printStackTrace();
        }
    }


}
