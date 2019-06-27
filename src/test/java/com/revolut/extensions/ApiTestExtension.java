package com.revolut.extensions;

import com.revolut.TestHelper;
import com.revolut.config.Config;
import com.revolut.router.Router;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

public class ApiTestExtension implements BeforeAllCallback, AfterAllCallback {
    private static final Logger logger = LoggerFactory.getLogger(ApiTestExtension.class);
    private long start;
    protected int PORT_USED = Config.getInt("api.spark.port");

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        start = System.currentTimeMillis();
        try {
            Spark.port(PORT_USED);
            new Router().init();
            TestHelper.initializeAccounts();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        logger.info("Spark server running in port: " + PORT_USED + ".... ");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        Spark.stop();
        Spark.awaitStop();
        logger.info("Spark server stopped... ");
        logger.info("Total test time: " + (System.currentTimeMillis() - start) + " ms.");
    }

}
