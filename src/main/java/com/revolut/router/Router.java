package com.revolut.router;

import com.revolut.controller.AccountController;
import com.revolut.controller.HealthController;
import com.revolut.controller.TransactionController;
import com.revolut.domain.transaction.Transaction;
import spark.Spark;
import spark.servlet.SparkApplication;

public class Router implements SparkApplication {
    @Override
    public void init() {
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
