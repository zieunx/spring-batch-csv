package com.danal.publicdataprocessor.util;

public class NumberUtils {

    static public int parseInt(String value, int defaultValue) {
        try {
            return value == null || value.isEmpty() ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
