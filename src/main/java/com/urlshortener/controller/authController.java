package com.urlshortener.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.urlshortener.service.AuthService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

public class AuthController implements HttpHandler {
    private final AuthService authService = new AuthService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        byte[] bodyBytes = exchange.getRequestBody().readAllBytes();
        String body = new String(bodyBytes);

        String username = null;
        String password = null;

        for (String param : body.split("&")) {
            String[] pair = param.split("=");
            if (pair.length == 2) {
                if ("username".equals(pair[0])) username = URLDecoder.decode(pair[1], "UTF-8");
                if ("password".equals(pair[0])) password = URLDecoder.decode(pair[1], "UTF-8");
            }
        }

        if (username == null || password == null) {
            exchange.sendResponseHeaders(400, -1);
            return;
        }

        boolean success = false;
        if ("POST".equals(method)) {
            String path = exchange.getRequestURI().getPath();
            if ("/login".equals(path)) {
                success = authService.login(username, password);
            } else if ("/register".equals(path)) {
                success = authService.register(username, password);
            }
        }

        String response = success ? "Success" : "Failed";
        exchange.sendResponseHeaders(success ? 200 : 401, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
