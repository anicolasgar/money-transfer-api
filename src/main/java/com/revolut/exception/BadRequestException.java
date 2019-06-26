package com.revolut.exception;

import javax.servlet.http.HttpServletResponse;

public class BadRequestException extends ApiException {

    private static final String CODE = "bad.request";

    public BadRequestException(String description) {
        super(CODE, description, HttpServletResponse.SC_BAD_REQUEST);
    }

    public BadRequestException(String description, Throwable cause) {
        super(CODE, description, HttpServletResponse.SC_BAD_REQUEST, cause);
    }
}
