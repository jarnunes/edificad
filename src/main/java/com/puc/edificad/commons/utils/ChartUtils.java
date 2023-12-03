package com.puc.edificad.commons.utils;

import java.util.Arrays;
import java.util.List;

public class ChartUtils {

    private static final List<String> PALETA_CORES_BARCHART = Arrays.asList(
            "rgb(255, 99, 132)",
            "rgb(255, 159, 64)",
            "rgb(255, 205, 86)",
            "rgb(75, 192, 192)",
            "rgb(153, 102, 255)",
            "rgb(201, 203, 207)");

    private static final List<String> PALETA_CORES_PIECHART = Arrays.asList(
            "rgb(52,224,178)",
            "rgb(224,101,34)",
            "rgb(255, 205, 86)",
            "rgb(0,224,150)",
            "rgb(0,152,224)",
            "rgb(206,224,107)");

    private ChartUtils() {

    }

    public static List<String> getPaletaCoresBarChartRGB() {
        return PALETA_CORES_BARCHART;
    }

    public static List<String> getPaletaCoresPieChartRGB() {
        return PALETA_CORES_PIECHART;
    }

    public static List<String> getPaletaCoresPieChartRGBA() {
        return getPaletaCoresPieChartRGB().stream().map(ChartUtils::toRGBA).toList();
    }

    public static List<String> getPaletaCoresRGBA() {
        return getPaletaCoresBarChartRGB().stream().map(ChartUtils::toRGBA).toList();
    }



    public static String toRGBA(String rgb) {
        final String[] rgbValues = rgb.replaceAll("[^0-9,]", "").split(",");

        int r = Integer.parseInt(rgbValues[0].trim());
        int g = Integer.parseInt(rgbValues[1].trim());
        int b = Integer.parseInt(rgbValues[2].trim());

        return String.format("rgba(%d, %d, %d, %s)", r, g, b, "0.2");
    }
}
