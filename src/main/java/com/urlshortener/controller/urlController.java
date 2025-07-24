package com.urlshortener.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.urlshortener.service.UrlService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Optional;

public class UrlController implements HttpHandler {
    private final UrlService urlService = new UrlService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            handleRedirect(exchange);
        } else if ("POST".equals(exchange.getRequestMethod())) {
            handleShorten(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleRedirect(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String code = path.substring(1); // Remove leading /

        Optional<String> redirectUrl = urlService.getOriginalUrl(code)
            .map(mapping -> mapping.getOriginalUrl());

        if (redirectUrl.isPresent()) {
            exchange.getResponseHeaders().add("Location", redirectUrl.get());
            exchange.sendResponseHeaders(302, -1);
        } else {
            byte[] response = "Short URL not found".getBytes();
            exchange.sendResponseHeaders(404, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        }
    }

    private void handleShorten(HttpExchange exchange) throws IOException {
        byte[] bodyBytes = exchange.getRequestBody().readAllBytes();
        String body = new String(bodyBytes);
        String[] params = body.split("&");

        String url = null;
        String username = "anonymous";
        for (String param : params) {
            String[] pair = param.split("=");
            if (pair.length == 2) {
                if ("url".equals(pair[0])) url = java.net.URLDecoder.decode(pair[1], "UTF-8");
                if ("username".equals(pair[0])) username = java.net.URLDecoder.decode(pair[1], "UTF-8");
            }
        }

        if (url != null && !url.isEmpty()) {
            String shortCode = urlService.shortenUrl(url, username);
            String response = "http://localhost:8080/" + shortCode;
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(400, -1);
        }
    }
}
