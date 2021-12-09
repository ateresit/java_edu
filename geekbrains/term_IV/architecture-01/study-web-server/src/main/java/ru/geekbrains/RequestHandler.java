package ru.geekbrains;

import ru.geekbrains.domain.HttpRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Deque;

import static ru.geekbrains.Config.WWW;

public class RequestHandler implements Runnable {

    private final SocketService socketService;
    private final RequestParser requestParser;

    public RequestHandler(SocketService socketService, RequestParser requestParser) {
        this.socketService = socketService;
        this.requestParser = requestParser;
    }

    @Override
    public void run() {

        Deque<String> rawRequest = socketService.readRequest();
        HttpRequest httpRequest = requestParser.parseRequest(rawRequest);
        StringBuilder response = new StringBuilder();

        if (httpRequest.getMethod().equals("GET")) {
            Path path = Paths.get(WWW, httpRequest.getUrl());

            if(!Files.exists(path)) {
                response.append("HTTP/1.1 404 NOT_FOUND\n");
                response.append("Content-Type: text/html; charset=utf-8\n");
                response.append("\n");
                response.append("<h1>Файл не найден!</h1>");
                socketService.writeResponse(response.toString());
                return;
            } else {

            }

            response.append("HTTP/1.1 200 OK\n");
            response.append("Content-Type: text/html; charset=utf-8");
            response.append("\n");

            try {
                Files.readAllLines(path).forEach(response::append);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            socketService.writeResponse(response.toString());
        } else {
            response.append("HTTP/1.1 405 METHOD_NOT_ALLOWED\n");
            response.append("Content-Type: text/html; charset=utf-8\n");
            response.append("\n");
            response.append("<h1>Метод не поддерживается!</h1>");
            socketService.writeResponse(response.toString());
            return;
        }
        try {
            socketService.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        System.out.println("Client disconnected!");

    }
}
