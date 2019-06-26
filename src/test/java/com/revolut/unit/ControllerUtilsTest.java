package com.revolut.unit;

import com.revolut.exception.BadRequestException;
import com.revolut.util.ControllerUtils;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import spark.Request;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerUtilsTest {

    @Test
    void getParamOk() throws BadRequestException {
        Request request = mock(Request.class);
        when(request.params("a")).thenReturn("value");

        String result = ControllerUtils.getParamFromRequest("a", request, false);

        Assertions.assertEquals("value", result);
    }

    @Test
    void getNonRequiredParamReturnsNull() throws BadRequestException {
        Request request = mock(Request.class);

        String result = ControllerUtils.getParamFromRequest("a", request, false);

        Assertions.assertNull(result);
    }

    @Test
    void getRequiredParamThrowsExecption() {
        Request request = mock(Request.class);

        Assertions.assertThrows(BadRequestException.class, () -> ControllerUtils.getParamFromRequest("a", request, true));
    }

    @Test
    void getDateParamOk() throws BadRequestException {
        Request request = mock(Request.class);
        when(request.queryParams("a")).thenReturn("20-03-19");

        DateTime result = ControllerUtils.getDateParamFromRequest("a", request, true);

        Assertions.assertEquals(DateTime.parse("20-03-19"), result);
    }

    @Test
    void getIntParamOk() throws BadRequestException {
        Request request = mock(Request.class);
        when(request.queryParams("a")).thenReturn("55");

        Integer result = ControllerUtils.getIntParamFromRequest("a", request, true);

        Assertions.assertEquals((Integer) 55, result);
    }

    @Test
    void getIntParamThrowsBadRequest() {
        Request request = mock(Request.class);
        when(request.queryParams("a")).thenReturn("Cincuenta y cinco");

        Assertions.assertThrows(BadRequestException.class, () -> ControllerUtils.getIntParamFromRequest("a", request, true));
    }
}
