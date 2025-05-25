package com.example.weather.util;

public class ValidationUtil {
    public static boolean isValidCityName(String city) {
        // Accept Unicode letters, spaces, and hyphens, 1-50 chars
        return city != null && city.matches("^[\\p{L}\\s\\-]{1,50}$");
    }
}
