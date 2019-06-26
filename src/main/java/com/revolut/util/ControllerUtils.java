package com.revolut.util;

import com.revolut.exception.BadRequestException;
import org.joda.time.DateTime;
import spark.Request;

public abstract class ControllerUtils {

    public static String getParamFromRequest(String parameterName, Request request, boolean required) throws BadRequestException {
        String value = request.params(parameterName);
        validateParameter(parameterName, value, required);
        return value;
    }

    public static String getQueryParamFromRequest(String parameterName, Request request, boolean required) throws BadRequestException {
        String value = request.queryParams(parameterName);
        validateParameter(parameterName, value, required);
        return value;
    }

    public static DateTime getDateParamFromRequest(String parameterName, Request request, boolean required) throws BadRequestException {
        String value = getQueryParamFromRequest(parameterName, request, required);
        return value != null && !value.isEmpty() ? DateTime.parse(value) : null;
    }

    public static Integer getIntParamFromRequest(String parameterName, Request request, boolean required) throws BadRequestException {
        String value = getQueryParamFromRequest(parameterName, request, required);
        try {
            return value != null ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            throw new BadRequestException("Param " + parameterName + " must be a decimal number");
        }
    }

    public static boolean getBoolQueryParamFromRequest(String parameterName, Request request, boolean required) throws BadRequestException {
        String value = request.queryParams(parameterName);
        validateParameter(parameterName, value, required);
        return Boolean.parseBoolean(value);
    }

    private static void validateParameter(String parameterName, String value, boolean required) throws BadRequestException {
        if (required && (value == null || value.isEmpty())) {
            throw new BadRequestException("Parameter " + parameterName + " is required");
        }
    }
}
