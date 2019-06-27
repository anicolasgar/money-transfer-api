package com.revolut.integration;

import com.google.gson.Gson;
import com.revolut.TestHelper;
import com.revolut.datatransferobject.AccountDTO;
import com.revolut.enums.Currency;
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
public class AccountIntegrationTest {

    @Test
    void testCreateAccount() throws IOException {
        final AccountDTO accountDTO = new AccountDTO(Currency.USD, true, new BigDecimal(987654));
        final String jsonPayload = JsonUtils.make().toJson(accountDTO);

        HttpResponse response = TestHelper.makePostRequest(TestHelper.getBaseUrl() + "/accounts", jsonPayload);
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatusLine().getStatusCode());

        final String json = TestHelper.getJsonFromResponse(response);
        AccountDTO responseAccount = new Gson().fromJson(json, AccountDTO.class);

        assertEquals(Currency.USD, responseAccount.getCurrency());
        assertEquals(true, responseAccount.isActive());
        assertEquals(new BigDecimal(987654), responseAccount.getBalance());
    }

    @Test
    void testCreateAccountWithInvalidFieldsShouldReturnBadRequest() throws IOException {
        final AccountDTO accountDTO = new AccountDTO(Currency.USD, true, new BigDecimal(-98765));
        final String jsonPayload = JsonUtils.make().toJson(accountDTO);

        HttpResponse response = TestHelper.makePostRequest(TestHelper.getBaseUrl() + "/accounts", jsonPayload);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
        final String json = TestHelper.getJsonFromResponse(response);
        assertTrue(json.contains("balance cannot be negative"));
    }

    @Test
    void testDeleteAccount() throws IOException {
        Long accountId = 1L;
        HttpResponse response = TestHelper.makeGetRequest(TestHelper.getBaseUrl() + "/accounts/" + accountId, null);
        assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());
        final String json = TestHelper.getJsonFromResponse(response);
        AccountDTO responseAccount = new Gson().fromJson(json, AccountDTO.class);
        assertEquals(accountId, responseAccount.getId());

        response = TestHelper.makeDeleteRequest(TestHelper.getBaseUrl() + "/accounts/" + accountId);
        final String json2 = TestHelper.getJsonFromResponse(response);
        assertEquals("true", json2);

        response = TestHelper.makeGetRequest(TestHelper.getBaseUrl() + "/accounts/" + accountId, null);
        assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }
}
