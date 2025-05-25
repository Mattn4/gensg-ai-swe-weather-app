package com.example.weather.service;

import com.example.weather.api.WeatherApiClient;
import com.example.weather.model.WeatherData;
import com.example.weather.util.JsonUtil;
import com.example.weather.util.ValidationUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.net.URLEncoder;

public class WeatherService {

    /**
     * Retrieves the current weather information for a given city name.
     *
     * @param cityName The name of the city for which to retrieve weather information.
     * @return A JSON string with the weather data or an error message.
     */
    public static String getWeatherForCity(String cityName) {
        if (cityName == null || cityName.trim().isEmpty()) {
            return "{\"error\": \"City not found.\"}";
        }
        if (!ValidationUtil.isValidCityName(cityName)) {
            return "{\"error\": \"Invalid city name.\"}";
        }
        try {
            double[] coords = getCoordinatesForCity(cityName);
            if (coords == null) {
                return "{\"error\": \"City not found.\"}";
            }
            double latitude = coords[0];
            double longitude = coords[1];

            WeatherData weather = getCurrentWeather(latitude, longitude);
            if (weather == null) {
                return "{\"error\": \"Weather data not found.\"}";
            }

            String description = getWeatherDescription(weather.weatherCode);
            return String.format(
                "{\"city\": \"%s\", \"temperature_celsius\": %.1f, \"description\": \"%s\"}",
                cityName, weather.temperature, description
            );
        } catch (JsonSyntaxException e) {
            // Log e internally if needed
            return "{\"error\": \"Invalid response format.\"}";
        } catch (Exception e) {
            // Log e internally if needed
            return "{\"error\": \"Network or parsing error.\"}";
        }
    }

    private static double[] getCoordinatesForCity(String cityName) throws Exception {
        String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                URLEncoder.encode(cityName, "UTF-8");
        String geoResponse = WeatherApiClient.getApiResponse(geoUrl);
        JsonObject geoJson = JsonUtil.parseJson(geoResponse);
        JsonArray results = geoJson.getAsJsonArray("results");
        if (results == null || results.size() == 0) return null;
        JsonObject firstResult = results.get(0).getAsJsonObject();
        if (!firstResult.has("latitude") || !firstResult.has("longitude")) return null;
        return new double[] {
            firstResult.get("latitude").getAsDouble(),
            firstResult.get("longitude").getAsDouble()
        };
    }

    private static WeatherData getCurrentWeather(double latitude, double longitude) throws Exception {
        String weatherUrl = String.format(
            "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true",
            latitude, longitude);
        String weatherResponse = WeatherApiClient.getApiResponse(weatherUrl);
        JsonObject weatherJson = JsonUtil.parseJson(weatherResponse);
        JsonObject currentWeather = weatherJson.getAsJsonObject("current_weather");
        if (currentWeather == null ||
            !currentWeather.has("temperature") ||
            !currentWeather.has("weathercode")) return null;
        return new WeatherData(
            currentWeather.get("temperature").getAsDouble(),
            currentWeather.get("weathercode").getAsInt()
        );
    }

    // Simple mapping for weather codes (expand as needed)
    private static String getWeatherDescription(int code) {
        switch (code) {
            case 0: return "Clear sky";
            case 1: return "Mainly clear";
            case 2: return "Partly cloudy";
            case 3: return "Cloudy";
            case 45: return "Fog";
            case 48: return "Depositing rime fog";
            case 51: return "Light drizzle";
            case 53: return "Moderate drizzle";
            case 55: return "Dense drizzle";
            case 61: return "Slight rain";
            case 63: return "Moderate rain";
            case 65: return "Heavy rain";
            case 71: return "Slight snow";
            case 73: return "Moderate snow";
            case 75: return "Heavy snow";
            case 80: return "Slight rain showers";
            case 81: return "Moderate rain showers";
            case 82: return "Violent rain showers";
            case 95: return "Thunderstorm";
            case 96: return "Thunderstorm, slight hail";
            case 99: return "Thunderstorm, heavy hail";
            default: return "Unknown";
        }
    }
}
