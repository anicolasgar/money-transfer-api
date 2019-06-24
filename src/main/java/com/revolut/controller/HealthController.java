package com.revolut.controller;

import spark.Request;
import spark.Response;

public class HealthController {

    public static Object ping(Request request, Response response) throws Exception {
        return "pong";
    }
}
