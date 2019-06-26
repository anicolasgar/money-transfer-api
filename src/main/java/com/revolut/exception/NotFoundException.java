package com.revolut.exception;

import javax.servlet.http.HttpServletResponse;

public class NotFoundException  extends ApiException {

    private static final String CODE = "not.found";

    public NotFoundException(String description) {
        super(CODE, description, HttpServletResponse.SC_NOT_FOUND);
    }
}
