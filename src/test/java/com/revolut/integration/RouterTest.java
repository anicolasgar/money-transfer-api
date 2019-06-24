package com.revolut.integration;

import com.revolut.extensions.ApiTestExtension;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(ApiTestExtension.class)
class RouterTest {

    @Test
    void ping() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/ping");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());
        assertEquals("pong", EntityUtils.toString(response.getEntity()));
    }
}
