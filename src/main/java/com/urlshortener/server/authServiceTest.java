package com.urlshortener.service;

import com.urlshortener.db.UserRepository;
import com.urlshortener.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private UserRepository mockRepo;
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        mockRepo = mock(UserRepository.class);
        authService = new AuthService(mockRepo); // Overloaded constructor for test
    }

    @Test
    public void testRegisterSuccess() {
        doNothing().when(mockRepo).save(any(User.class));
        boolean result = authService.register("testuser", "testpass");

        assertTrue(result);
        verify(mockRepo, times(1)).save(any(User.class));
    }

    @Test
    public void testLoginSuccess() {
        when(mockRepo.authenticate("user1", "pass1")).thenReturn(true);

        boolean result = authService.login("user1", "pass1");

        assertTrue(result);
        verify(mockRepo).authenticate("user1", "pass1");
    }

    @Test
    public void testLoginFail() {
        when(mockRepo.authenticate("user2", "wrongpass")).thenReturn(false);

        boolean result = authService.login("user2", "wrongpass");

        assertFalse(result);
    }
}
