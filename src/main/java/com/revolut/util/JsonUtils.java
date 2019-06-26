/*
 * Copyright (c) 2018. Ivan Vakhrushev. All rights reserved.
 * https://github.com/mfvanek
 */

package com.revolut.util;

import com.google.gson.Gson;
import spark.Response;
//import lombok.Getter;
//import lombok.ToString;
//import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

    private JsonUtils() {}

    public static Gson make() {
        return new Gson().newBuilder().setPrettyPrinting().create();
    }

    public static String toJson(Exception err, int errCode) {
        final ErrorInfo info = new ErrorInfo(err, errCode);
        return make().toJson(info);
    }

    private static class ErrorInfo {

        private final int errorCode;
        private final String errorMessage;

        ErrorInfo(Exception err, int errCode) {
            this.errorCode = errCode;
            this.errorMessage = err.getLocalizedMessage();
        }
    }

}
