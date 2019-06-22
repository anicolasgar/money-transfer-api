package com.revolut.controller;

import com.revolut.datatransferobject.TransactionDTO;
import com.revolut.domain.transaction.Transaction;
import com.revolut.enums.TransactionState;
import com.revolut.service.TransactionService;
import com.revolut.util.JsonUtils;
import spark.Request;
import spark.Response;

public class TransactionController {
    private static TransactionService transactionService;

    public static Object getById(Request request, Response response) throws Exception {
        Long transactionId = Long.parseLong(request.params(":id"));
        return transactionService.INSTANCE.findById(transactionId);
    }

    public static Object createTransaction(Request request, Response response) throws Exception {
        final TransactionDTO transactionDTO = JsonUtils.make().fromJson(request.body(), TransactionDTO.class);
        Transaction transaction = transactionService.INSTANCE.create(transactionDTO);
        return transaction;
    }

    public static Object delete(Request request, Response response) throws Exception {
        Long transactionId = Long.parseLong(request.params(":id"));
        transactionService.INSTANCE.delete(transactionId);
        return "";
    }

}
