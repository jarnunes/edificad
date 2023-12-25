package com.puc.edificad.commons.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class ServletUtils {

    private ServletUtils() {
    }

    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
    }

    public static String toQueryString(Map<String, Object> queryStringParameters) {
        if (MapUtils.isEmpty(queryStringParameters))
            return StringUtils.EMPTY;

        StringBuilder sbUrl = new StringBuilder("?");
        queryStringParameters.forEach((key, value) -> {
            sbUrl.append(key).append("=").append(value);
        });

        return sbUrl.toString();
    }
}
