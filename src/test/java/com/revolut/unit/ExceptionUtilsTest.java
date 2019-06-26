package com.revolut.unit;

import com.revolut.exception.ApiException;
import com.revolut.util.ExceptionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionUtilsTest {
    @Test
    void testGetFromChain() {
        Exception e1 = new RuntimeException();
        Exception e2 = new ApiException("", "", e1);

        Assertions.assertEquals(e2, ExceptionUtils.getFromChain(e2, RuntimeException.class));
    }
}
