package com.revolut.controller;

import com.revolut.datatransferobject.AccountDTO;
import com.revolut.domain.account.Account;
import com.revolut.service.AccountService;
import com.revolut.util.JsonUtils;
import spark.Request;
import spark.Response;
import spark.Spark;

import static spark.Spark.path;

public class AccountController {

    private static AccountService accountService;

    public static Object getById(Request request, Response response) throws Exception {
        Long accountId = Long.parseLong(request.params(":id"));
        return accountService.INSTANCE.findById(accountId);
    }

    public static Object createAccount(Request request, Response response) throws Exception {
        final AccountDTO accountDTO = JsonUtils.make().fromJson(request.body(), AccountDTO.class);
        Account account = accountService.INSTANCE.create(accountDTO);
        return account;
    }

    public static Object delete(Request request, Response response) throws Exception {
        Long accountId = Long.parseLong(request.params(":id"));
        accountService.INSTANCE.delete(accountId);
        return "";
    }

}
