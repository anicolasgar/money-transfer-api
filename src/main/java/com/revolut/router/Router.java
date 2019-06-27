package com.revolut.router;

import com.revolut.controller.AccountController;
import com.revolut.controller.HealthController;
import com.revolut.controller.TransactionController;
import com.revolut.exception.ApiException;
import com.revolut.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Response;
import spark.Spark;
import spark.servlet.SparkApplication;

import javax.servlet.http.HttpServletResponse;

public class Router implements SparkApplication {
    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    @Override
    public void init() {
        Spark.before((request, response) -> request.body());
        Spark.after((request, response) -> setHeaders(response));

        Spark.notFound((request, response) -> {
            response.header("Content-Type", "application/json");

            response.status(HttpServletResponse.SC_NOT_FOUND);

            ApiException e = new ApiException("route_not_found", String.format("Route %s not found", request.uri()), HttpServletResponse.SC_NOT_FOUND);

            return e.toJson();
        });

        Spark.exception(Exception.class, (e, request, response) -> {
            Throwable t = ExceptionUtils.getFromChain(e, ApiException.class);

            ApiException apiException = t instanceof ApiException ? (ApiException) t : new ApiException("internal_error", "Internal server error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Internal error", e);
            response.status(apiException.getStatusCode());
            response.body(apiException.toJson());
            setHeaders(response);
        });

        defineEndpoints();
    }

    private void setHeaders(Response response) {
        if (!response.raw().containsHeader("Content-Type")) {
            response.header("Content-Type", "application/json");
        }
    }

    private void defineEndpoints() {
        Spark.get("/ping", HealthController::ping);

        Spark.get("/accounts/:id", AccountController::getById);
        Spark.post("/accounts", AccountController::createAccount);
        Spark.delete("/accounts/:id", AccountController::delete);

        Spark.get("/transactions/:id", TransactionController::getById);
        Spark.post("/transactions", TransactionController::createTransaction);
        Spark.delete("/transactions/:id", TransactionController::delete);
    }


    @Override
    public void destroy() {
    }


}
