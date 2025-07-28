package com.urlshortener.service;

import com.urlshortener.db.UrlRepository;
import com.urlshortener.model.UrlMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UrlServiceTest {

    private UrlRepository mockRepo;
    private UrlService urlService;

    @BeforeEach
    public void setUp() {
        mockRepo = mock(UrlRepository.class);
        urlService = new UrlService(mockRepo); // Overloaded constructor for test
    }

    @Test
    public void testShortenUrl() {
        String code = urlService.shortenUrl("https://example.com", "anonymous");

        assertNotNull(code);
        assertEquals(6, code.length());
        verify(mockRepo, times(1)).save(any(UrlMapping.class));
    }

    @Test
    public void testGetOriginalUrlFound() {
        UrlMapping mapping = new UrlMapping("abc123", "https://example.com", "anonymous");
        when(mockRepo.findByShortCode("abc123")).thenReturn(Optional.of(mapping));

        Optional<UrlMapping> result = urlService.getOriginalUrl("abc123");

        assertTrue(result.isPresent());
        assertEquals("https://example.com", result.get().getOriginalUrl());
    }

    @Test
    public void testGetOriginalUrlNotFound() {
        when(mockRepo.findByShortCode("xyz999")).thenReturn(Optional.empty());

        Optional<UrlMapping> result = urlService.getOriginalUrl("xyz999");

        assertFalse(result.isPresent());
    }
}
