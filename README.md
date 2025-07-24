# URL Shortener (No Frameworks)

This is a minimal URL shortener built without any frameworks.

## 🧱 Tech Stack

- Java (`HttpServer`)
- H2 Database (via JDBC)
- HTML/CSS/JS (Vanilla)
- SLF4J Logging
- JUnit 5 & Mockito for testing

## ✨ Features

- Anonymous URL shortening
- Registered users can shorten custom URLs
- Redirect short URL to original URL
- Login & Register endpoints
- GitHub Actions for CI/CD

## 🚀 How to Run

```bash
mvn clean install
java -cp target/url-shortener-1.0-SNAPSHOT.jar com.urlshortener.server.HttpServerApp
