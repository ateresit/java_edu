package ru.geekbrains;

import ru.geekbrains.domain.HttpRequest;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public HttpRequest parseRequest(Deque<String> rawRequest) {
        String[] firstLine = rawRequest.pollFirst().split(" "); // разделитель пробел, т.к. запрос вида: GET /some/file.txt HTTP/1.1
        String method = firstLine[0];
        String url = firstLine[1];

        Map<String,String> headers = new HashMap<>();
        while (!rawRequest.isEmpty()) {
            String line = rawRequest.pollFirst();
            if (line.isBlank()) {
                break; // т.к. далее идёт тело запроса
            }
            String[] header = line.split(": ");
            headers.put(header[0], header[1]);
        }
        StringBuilder body = new StringBuilder();

        while (!rawRequest.isEmpty()) {
            body.append(rawRequest.pollFirst());
        }
        return new HttpRequest(method, url, headers, body.toString());
    }
}
