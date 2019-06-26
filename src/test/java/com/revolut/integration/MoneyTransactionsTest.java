package com.revolut.integration;

import com.google.gson.Gson;
import com.revolut.TestHelper;
import com.revolut.datatransferobject.TransactionDTO;
import com.revolut.domain.transaction.Transaction;
import com.revolut.enums.TransactionState;
import com.revolut.exception.ApiException;
import com.revolut.extensions.ApiTestExtension;
import com.revolut.util.JsonUtils;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApiTestExtension.class)
public class MoneyTransactionsTest {

    @Test
    void testMakeASuccessfulTransactionBetweenAccounts() throws IOException {
        final TransactionDTO transaction = new TransactionDTO(2L, 3L, new BigDecimal(5));
        final String jsonPayload = JsonUtils.make().toJson(transaction);

        HttpResponse response = TestHelper.makePostRequest("http://localhost:8080/transactions", jsonPayload);
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatusLine().getStatusCode());

        final String json = TestHelper.getJsonFromResponse(response);
        Transaction responseTransaction = new Gson().fromJson(json, Transaction.class);

        assertEquals(2L, (long) responseTransaction.getDebitAccount());
        assertEquals(3L, (long) responseTransaction.getCreditAccount());
        assertEquals(new BigDecimal(5), responseTransaction.getAmount());
        assertEquals(TransactionState.COMPLETED, responseTransaction.getTransactionState());
    }

    @Test
    void testMakeATransactionBetweenAccountsWithoutFunds() throws IOException {
        final TransactionDTO transaction = new TransactionDTO(3L, 2L, new BigDecimal(5));
        final String jsonPayload = JsonUtils.make().toJson(transaction);

        HttpResponse response = TestHelper.makePostRequest("http://localhost:8080/transactions", jsonPayload);
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatusLine().getStatusCode());

        final String json = TestHelper.getJsonFromResponse(response);
        Transaction responseTransaction = new Gson().fromJson(json, Transaction.class);

        assertEquals(3L, (long) responseTransaction.getDebitAccount());
        assertEquals(2L, (long) responseTransaction.getCreditAccount());
        assertEquals(new BigDecimal(5), responseTransaction.getAmount());
        assertEquals(TransactionState.INSUFFICIENT_FUNDS, responseTransaction.getTransactionState());
    }

    @Test
    void testMakeATransactionProvidingAnInvalidDebitAccount() throws IOException {
        final TransactionDTO transaction = new TransactionDTO(30L, 2L, new BigDecimal(5));
        final String jsonPayload = JsonUtils.make().toJson(transaction);

        HttpResponse response = TestHelper.makePostRequest("http://localhost:8080/transactions", jsonPayload);
        assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
        final String json = TestHelper.getJsonFromResponse(response);
        assertTrue(json.contains("account 30 not found"));
    }

    @Test
    void testMakeATransactionProvidingAnInvalidCreditAccount() throws IOException {
        final TransactionDTO transaction = new TransactionDTO(3L, 20L, new BigDecimal(5));
        final String jsonPayload = JsonUtils.make().toJson(transaction);

        HttpResponse response = TestHelper.makePostRequest("http://localhost:8080/transactions", jsonPayload);
        assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
        final String json = TestHelper.getJsonFromResponse(response);
        assertTrue(json.contains("account 20 not found"));
    }

    @Test
    void testMakeATransactionProvidingAnInvalidAmount() throws IOException {
        final TransactionDTO transaction = new TransactionDTO(3L, 2L, new BigDecimal(-5));
        final String jsonPayload = JsonUtils.make().toJson(transaction);

        HttpResponse response = TestHelper.makePostRequest("http://localhost:8080/transactions", jsonPayload);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
        final String json = TestHelper.getJsonFromResponse(response);
        assertTrue(json.contains("amount cannot be negative"));
    }

    @Test
    void testCreateAccountWithInvalidFieldsShouldReturnBadRequest() throws IOException {
        //TODO
    }


}
