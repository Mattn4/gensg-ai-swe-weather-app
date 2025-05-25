package com.example.weather.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherApiClientTest {

    @Test
    public void testRejectsNonHttpsUrl() {
        Exception exception = assertThrows(Exception.class, () -> {
            WeatherApiClient.getApiResponse("http://example.com");
        });
        assertTrue(exception.getMessage().contains("HTTPS required"));
    }
}
