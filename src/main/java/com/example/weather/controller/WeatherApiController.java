package com.example.weather.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import java.util.*;
import com.example.weather.service.WeatherService;
import com.example.weather.util.JsonUtil;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api")
public class WeatherApiController {

    static class WeatherRequest {
        public List<String> cities;
    }

    static class WeatherResult {
        public String city;
        public Double temperature_celsius;
        public String description;
        public String error;
    }

    @PostMapping(value = "/weather", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getWeather(@RequestBody WeatherRequest req) {
        Map<String, Object> response = new HashMap<>();
        if (req.cities == null || req.cities.isEmpty() || req.cities.size() > 5) {
            response.put("error", "Please provide between 1 and 5 city names.");
            return response;
        }
        List<WeatherResult> results = new ArrayList<>();
        for (String city : req.cities) {
            String json = WeatherService.getWeatherForCity(city);
            WeatherResult result = new WeatherResult();
            result.city = city;
            try {
                JsonObject obj = JsonUtil.parseJson(json);
                if (obj.has("error")) {
                    result.error = obj.get("error").getAsString();
                } else {
                    result.temperature_celsius = obj.get("temperature_celsius").getAsDouble();
                    result.description = obj.get("description").getAsString();
                }
            } catch (Exception e) {
                result.error = "Parsing error";
            }
            results.add(result);
        }
        response.put("results", results);
        return response;
    }
}
