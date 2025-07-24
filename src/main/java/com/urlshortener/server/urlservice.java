package com.urlshortener.service;

import com.urlshortener.db.UrlRepository;
import com.urlshortener.model.UrlMapping;

import java.util.Optional;
import java.util.Random;

public class UrlService {
    private final UrlRepository repository = new UrlRepository();

    public String generateShortCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }

    public String shortenUrl(String originalUrl, String username) {
        String code = generateShortCode();
        UrlMapping mapping = new UrlMapping(code, originalUrl, username);
        repository.save(mapping);
        return code;
    }

    public Optional<UrlMapping> getOriginalUrl(String code) {
        return repository.findByShortCode(code);
    }
}
