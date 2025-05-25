package com.example.weather.controller;

import com.example.weather.service.WeatherService;
import com.example.weather.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherApiController.class)
public class WeatherApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testValidCitiesReturnsWeather() throws Exception {
        String mockJson = "{\"temperature_celsius\":25.0,\"description\":\"Mainly clear\"}";
        try (MockedStatic<WeatherService> wsMock = Mockito.mockStatic(WeatherService.class);
             MockedStatic<JsonUtil> juMock = Mockito.mockStatic(JsonUtil.class)) {
            wsMock.when(() -> WeatherService.getWeatherForCity(anyString())).thenReturn(mockJson);
            juMock.when(() -> JsonUtil.parseJson(anyString())).thenCallRealMethod();

            String requestBody = "{\"cities\": [\"London\"]}";
            mockMvc.perform(post("/api/weather")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.results[0].temperature_celsius").value(25.0))
                    .andExpect(jsonPath("$.results[0].description").value("Mainly clear"));
        }
    }

    @Test
    void testTooManyCitiesReturnsError() throws Exception {
        String requestBody = "{\"cities\": [\"A\",\"B\",\"C\",\"D\",\"E\",\"F\"]}";
        mockMvc.perform(post("/api/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void testEmptyCitiesReturnsError() throws Exception {
        String requestBody = "{\"cities\": []}";
        mockMvc.perform(post("/api/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void testNonExistentCityReturnsError() throws Exception {
        String mockJson = "{\"error\":\"City not found\"}";
        try (MockedStatic<WeatherService> wsMock = Mockito.mockStatic(WeatherService.class);
             MockedStatic<JsonUtil> juMock = Mockito.mockStatic(JsonUtil.class)) {
            wsMock.when(() -> WeatherService.getWeatherForCity(anyString())).thenReturn(mockJson);
            juMock.when(() -> JsonUtil.parseJson(anyString())).thenCallRealMethod();

            String requestBody = "{\"cities\": [\"Atlantis\"]}";
            mockMvc.perform(post("/api/weather")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.results[0].error").value("City not found"));
        }
    }

    @Test
    void testApiFailureScenario() throws Exception {
        String mockJson = "{\"error\":\"Network or parsing error\"}";
        try (MockedStatic<WeatherService> wsMock = Mockito.mockStatic(WeatherService.class);
             MockedStatic<JsonUtil> juMock = Mockito.mockStatic(JsonUtil.class)) {
            wsMock.when(() -> WeatherService.getWeatherForCity(anyString())).thenReturn(mockJson);
            juMock.when(() -> JsonUtil.parseJson(anyString())).thenCallRealMethod();

            String requestBody = "{\"cities\": [\"London\"]}";
            mockMvc.perform(post("/api/weather")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.results[0].error").value("Network or parsing error"));
        }
    }
}
