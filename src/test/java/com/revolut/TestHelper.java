package com.revolut;

import com.revolut.config.Config;
import com.revolut.datatransferobject.AccountDTO;
import com.revolut.enums.Currency;
import com.revolut.exception.ConstraintsViolationException;
import com.revolut.service.AccountService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestHelper {

    public static void initializeAccounts() throws ConstraintsViolationException {
        AccountDTO accountDTO = new AccountDTO(1L, Currency.USD, true, new BigDecimal(100));
        AccountService.INSTANCE.create(accountDTO);

        accountDTO = new AccountDTO(2L, Currency.USD, true, new BigDecimal(50));
        AccountService.INSTANCE.create(accountDTO);

        accountDTO = new AccountDTO(3L, Currency.USD, true, new BigDecimal(0));
        AccountService.INSTANCE.create(accountDTO);
    }

    public static String getJsonFromResponse(HttpResponse response) throws IOException {
        final HttpEntity entity = response.getEntity();
        assertNotNull(entity);
        assertEquals("application/json", entity.getContentType().getValue());
        final String json = EntityUtils.toString(entity);

        return json;
    }

    public static HttpResponse makePostRequest(String uri, String jsonPayload) throws IOException {
        StringEntity requestEntity = new StringEntity(
                jsonPayload,
                ContentType.APPLICATION_JSON);

        HttpPost postMethod = new HttpPost(uri);
        postMethod.setEntity(requestEntity);
        HttpResponse response = HttpClientBuilder.create().build().execute(postMethod);
        return response;
    }

    public static HttpResponse makeGetRequest(String uri, String params) throws IOException {
        String uriWithParams = params != null && !params.isEmpty() ? uri + "?" + params : uri;
        HttpGet getMethod = new HttpGet(uriWithParams);
        HttpResponse response = HttpClientBuilder.create().build().execute(getMethod);
        return response;
    }


    public static HttpResponse makeDeleteRequest(String uri) throws IOException {
        HttpDelete deleteMethod = new HttpDelete(uri);
        HttpResponse response = HttpClientBuilder.create().build().execute(deleteMethod);
        return response;
    }

    public static String getBaseUrl(){
        return Config.getString("api.base.url") + ":" + Config.getInt("api.spark.port");

    }


}
