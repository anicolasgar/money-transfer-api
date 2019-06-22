package com.revolut;

import com.revolut.router.Router;
import spark.Spark;
import spark.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

    private Main() {

    }

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        final String prop = System.getProperty("spark.port");
        final int port = StringUtils.isNotBlank(prop) ? Integer.parseInt(prop) : 8080;

        Spark.port(port);
        new Router().init();

        log.info("Listening on http://localhost:8080/");
    }

}
