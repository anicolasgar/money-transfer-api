package com.revolut.util;


import java.math.BigDecimal;

public class Validator {
    public static void validateAmountNotNegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

}
