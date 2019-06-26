package com.revolut.controller;

import com.revolut.datatransferobject.TransactionDTO;
import com.revolut.domain.transaction.Transaction;
import com.revolut.enums.TransactionState;
import com.revolut.service.TransactionService;
import com.revolut.util.JsonUtils;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;

public class TransactionController {

    public static Object getById(Request request, Response response) throws Exception {
        Long transactionId = Long.parseLong(request.params(":id"));

        response.status(HttpServletResponse.SC_OK);
        Transaction transaction= TransactionService.INSTANCE.findById(transactionId);
        return JsonUtils.make().toJson(transaction);
    }

    public static Object createTransaction(Request request, Response response) throws Exception {
        final TransactionDTO transactionDTO = JsonUtils.make().fromJson(request.body(), TransactionDTO.class);
        Transaction transaction = TransactionService.INSTANCE.create(transactionDTO);

        response.status(HttpServletResponse.SC_CREATED);
        return JsonUtils.make().toJson(transaction);
    }

    public static Object delete(Request request, Response response) throws Exception {
        Long transactionId = Long.parseLong(request.params(":id"));
        TransactionService.INSTANCE.delete(transactionId);

        response.status(HttpServletResponse.SC_OK);
        return JsonUtils.make().toJson(true);
    }

}
