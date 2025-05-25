package com.example.weather.service;

import com.example.weather.model.WeatherData;
import com.example.weather.api.WeatherApiClient;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherServiceTest {

    @Test
    public void testValidCityReturnsWeather() throws Exception {
        String geoJson = "{ \"results\": [ { \"latitude\": 1.29, \"longitude\": 103.85 } ] }";
        String weatherJson = "{ \"current_weather\": { \"temperature\": 30.0, \"weathercode\": 1 } }";
        try (MockedStatic<WeatherApiClient> mocked = Mockito.mockStatic(WeatherApiClient.class)) {
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.contains("geocoding-api"))).thenReturn(geoJson);
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.contains("forecast"))).thenReturn(weatherJson);

            String result = WeatherService.getWeatherForCity("Singapore");
            assertTrue(result.contains("\"city\": \"Singapore\""));
            assertTrue(result.contains("\"temperature_celsius\": 30.0"));
            assertTrue(result.contains("\"description\": \"Mainly clear\""));
        }
    }

    @Test
    public void testNonExistentCityReturnsError() throws Exception {
        String geoJson = "{ \"results\": [] }";
        try (MockedStatic<WeatherApiClient> mocked = Mockito.mockStatic(WeatherApiClient.class)) {
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.anyString())).thenReturn(geoJson);

            String result = WeatherService.getWeatherForCity("Atlantis");
            assertTrue(result.contains("City not found"));
        }
    }

    @Test
    public void testEmptyInputReturnsError() {
        String result = WeatherService.getWeatherForCity("");
        assertTrue(result.contains("City not found"));
    }

    @Test
    public void testApiFailureReturnsError() throws Exception {
        try (MockedStatic<WeatherApiClient> mocked = Mockito.mockStatic(WeatherApiClient.class)) {
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.anyString()))
                  .thenThrow(new RuntimeException("Simulated API failure"));

            String result = WeatherService.getWeatherForCity("Singapore");
            assertTrue(result.contains("Network or parsing error"));
        }
    }

    // Edge case: Invalid city name
    @Test
    public void testInvalidCityNameReturnsError() {
        String result = WeatherService.getWeatherForCity("City@123");
        assertTrue(result.contains("Invalid city name"));
    }

    // Edge case: Malformed API response (geo)
    @Test
    public void testMalformedGeoJsonReturnsError() throws Exception {
        String malformedJson = "{ this is not valid json ";
        try (MockedStatic<WeatherApiClient> mocked = Mockito.mockStatic(WeatherApiClient.class)) {
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.anyString())).thenReturn(malformedJson);

            String result = WeatherService.getWeatherForCity("Singapore");
            assertTrue(result.contains("Invalid response format") || result.contains("parsing error"));
        }
    }

    // Edge case: Malformed weather API response (missing temperature)
    @Test
    public void testMalformedWeatherJsonReturnsError() throws Exception {
        String geoJson = "{ \"results\": [ { \"latitude\": 1.29, \"longitude\": 103.85 } ] }";
        String malformedWeatherJson = "{ \"current_weather\": { \"weathercode\": 1 } }"; // missing temperature
        try (MockedStatic<WeatherApiClient> mocked = Mockito.mockStatic(WeatherApiClient.class)) {
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.contains("geocoding-api"))).thenReturn(geoJson);
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.contains("forecast"))).thenReturn(malformedWeatherJson);

            String result = WeatherService.getWeatherForCity("Singapore");
            assertTrue(result.contains("Weather data not found") || result.contains("error"));
        }
    }

    // Edge case: Unknown weather code
    @Test
    public void testUnknownWeatherCodeReturnsUnknownDescription() throws Exception {
        String geoJson = "{ \"results\": [ { \"latitude\": 1.29, \"longitude\": 103.85 } ] }";
        String weatherJson = "{ \"current_weather\": { \"temperature\": 25.0, \"weathercode\": 999 } }";
        try (MockedStatic<WeatherApiClient> mocked = Mockito.mockStatic(WeatherApiClient.class)) {
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.contains("geocoding-api"))).thenReturn(geoJson);
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.contains("forecast"))).thenReturn(weatherJson);

            String result = WeatherService.getWeatherForCity("Singapore");
            assertTrue(result.contains("\"description\": \"Unknown\""));
        }
    }

    // Edge case: API returns HTTP error (e.g., 500, 429)
    @Test
    public void testApiReturnsHttpError() throws Exception {
        try (MockedStatic<WeatherApiClient> mocked = Mockito.mockStatic(WeatherApiClient.class)) {
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.anyString()))
                .thenThrow(new RuntimeException("HTTP 500 Internal Server Error"));

            String result = WeatherService.getWeatherForCity("Singapore");
            assertTrue(result.contains("Network or parsing error") || result.contains("500"));
        }
    }

    // Edge case: International city names
    @Test
    public void testInternationalCityName() throws Exception {
        // Use São Paulo's real coordinates
        String geoJson = "{ \"results\": [ { \"latitude\": -23.55, \"longitude\": -46.63 } ] }";
        String weatherJson = "{ \"current_weather\": { \"temperature\": 18.0, \"weathercode\": 1 } }";
        try (MockedStatic<WeatherApiClient> mocked = Mockito.mockStatic(WeatherApiClient.class)) {
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.contains("geocoding-api"))).thenReturn(geoJson);
            mocked.when(() -> WeatherApiClient.getApiResponse(Mockito.contains("forecast"))).thenReturn(weatherJson);

            String result = WeatherService.getWeatherForCity("São Paulo");
            System.out.println("Result: " + result); // Debug output
            assertTrue(
                result.contains("\"city\": \"São Paulo\"") ||
                result.contains("\"city\": \"S\\u00e3o Paulo\"")
            );
            assertTrue(result.contains("\"temperature_celsius\": 18.0"));
        }
    }

    // Edge case: Concurrent requests (thread safety)
    @Test
    public void testConcurrentRequests() throws InterruptedException {
        Runnable task = () -> {
            String result = WeatherService.getWeatherForCity("London");
            assertNotNull(result);
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
