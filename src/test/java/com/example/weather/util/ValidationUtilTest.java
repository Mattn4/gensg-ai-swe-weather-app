package com.example.weather.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilTest {

    @Test
    public void testValidCityName() {
        assertTrue(ValidationUtil.isValidCityName("London"));
        assertTrue(ValidationUtil.isValidCityName("New York"));
        assertTrue(ValidationUtil.isValidCityName("San Francisco"));
        assertTrue(ValidationUtil.isValidCityName("Rio-de-Janeiro"));
    }

    @Test
    public void testInvalidCityName() {
        assertFalse(ValidationUtil.isValidCityName("City@123"));
        assertFalse(ValidationUtil.isValidCityName(""));
        assertFalse(ValidationUtil.isValidCityName(null));
        assertFalse(ValidationUtil.isValidCityName("A".repeat(51)));
    }
}
