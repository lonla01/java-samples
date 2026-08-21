package com.apptitude.employeemanager.model;

import java.util.Locale;

public enum Department {
    ENGINEERING,
    FINANCE,
    HR,
    SALES,
    MARKETING,
    OPERATIONS,
    PLATFORM,
    UNKNOWN;

    public static Department fromString(String value) {
        if (value == null || value.isBlank()) {
            return UNKNOWN;
        }

        String normalized = value.trim()
                .replace('-', '_')
                .replace(' ', '_')
                .toUpperCase(Locale.ROOT);

        try {
            return Department.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
