package com.example.weather.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class WeatherApiClient {
    public static String getApiResponse(String urlString) throws Exception {
        if (!urlString.startsWith("https://")) {
            throw new Exception("Insecure API URL: HTTPS required.");
        }
        URI uri = new URI(urlString);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(10000); // 10 seconds
        conn.setReadTimeout(10000);    // 10 seconds

        if (conn.getResponseCode() != 200) {
            throw new Exception("Failed to fetch data from: " + urlString);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }
}
