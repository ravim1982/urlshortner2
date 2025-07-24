package com.urlshortener.server;

import com.sun.net.httpserver.HttpServer;
import com.urlshortener.controller.AuthController;
import com.urlshortener.controller.UrlController;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerApp {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new UrlController());           // GET /abc123 → redirect
        server.createContext("/shorten", new UrlController());    // POST /shorten
        server.createContext("/login", new AuthController());     // POST /login
        server.createContext("/register", new AuthController());  // POST /register

        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Server started at http://localhost:8080");
    }
}
