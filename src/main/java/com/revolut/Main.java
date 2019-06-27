package com.revolut;

import com.revolut.config.Config;
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
        final String baseUrl = Config.getString("api.base.url");
        final int port = Config.hasKey("api.spark.port") ? Config.getInt("api.spark.port") : 8080;

        Spark.port(port);
        new Router().init();

        log.info("Listening on " + baseUrl + ":" + port);
    }

}
