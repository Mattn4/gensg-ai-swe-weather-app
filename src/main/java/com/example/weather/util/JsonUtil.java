package com.example.weather.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonUtil {
    public static JsonObject parseJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, JsonObject.class);
    }
}
