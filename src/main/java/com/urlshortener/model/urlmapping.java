package com.urlshortener.model;

public class UrlMapping {
    private String shortCode;
    private String originalUrl;
    private String username;

    public UrlMapping(String shortCode, String originalUrl, String username) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.username = username;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getUsername() {
        return username;
    }
}
s