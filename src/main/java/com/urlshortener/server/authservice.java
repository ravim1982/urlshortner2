package com.urlshortener.service;

import com.urlshortener.db.UserRepository;
import com.urlshortener.model.User;

public class AuthService {
    private final UserRepository userRepository = new UserRepository();

    public boolean register(String username, String password) {
        try {
            userRepository.save(new User(username, password));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean login(String username, String password) {
        return userRepository.authenticate(username, password);
    }
}
