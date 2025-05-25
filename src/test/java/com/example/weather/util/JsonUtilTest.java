package com.example.weather.util;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonUtilTest {

    @Test
    public void testParseValidJson() {
        String json = "{\"foo\": \"bar\", \"num\": 42}";
        JsonObject obj = JsonUtil.parseJson(json);
        assertEquals("bar", obj.get("foo").getAsString());
        assertEquals(42, obj.get("num").getAsInt());
    }

    @Test
    public void testParseMalformedJsonThrows() {
        String malformed = "{ this is not valid json ";
        assertThrows(Exception.class, () -> {
            JsonUtil.parseJson(malformed);
        });
    }
}
