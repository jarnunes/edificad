package com.puc.edificad.commons.utils;

public class BooUtils {
    private BooUtils() {
    }

    public static boolean toBoolean(Object value) {
        if (value == null)
            return false;

        final String strValue = value.toString().toLowerCase();
        return strValue.equalsIgnoreCase("true")
            || strValue.equalsIgnoreCase("1")
            || strValue.equalsIgnoreCase("yes")
            || strValue.equalsIgnoreCase("sim");
    }
}
