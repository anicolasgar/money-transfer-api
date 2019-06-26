package com.revolut.controller;

import com.revolut.datatransferobject.AccountDTO;
import com.revolut.domain.account.Account;
import com.revolut.service.AccountService;
import com.revolut.util.JsonUtils;
import spark.Request;
import spark.Response;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;

import static spark.Spark.path;

public class AccountController {

    public static Object getById(Request request, Response response) throws Exception {
        Long accountId = Long.parseLong(request.params(":id"));

        response.status(HttpServletResponse.SC_OK);
        Account account = AccountService.INSTANCE.findById(accountId);
        return JsonUtils.make().toJson(account);
    }

    public static Object createAccount(Request request, Response response) throws Exception {
        final AccountDTO accountDTO = JsonUtils.make().fromJson(request.body(), AccountDTO.class);
        Account account = AccountService.INSTANCE.create(accountDTO);
        response.status(HttpServletResponse.SC_CREATED);

        return JsonUtils.make().toJson(account);
    }

    public static Object delete(Request request, Response response) throws Exception {
        Long accountId = Long.parseLong(request.params(":id"));
        AccountService.INSTANCE.delete(accountId);

        response.status(HttpServletResponse.SC_OK);
        return JsonUtils.make().toJson(true);
    }

}
