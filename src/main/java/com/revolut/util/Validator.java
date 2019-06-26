package com.revolut.util;


import com.revolut.exception.ConstraintsViolationException;

import java.math.BigDecimal;

public class Validator {
    public static void validateAmountNotNegative(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ConstraintsViolationException("amount cannot be negative");
        }
    }

    public static void validateDifferentAccounts(Long account1, Long account2){
        if (account1.equals(account2)) {
            throw new ConstraintsViolationException("credit_account and debit_account is the same account");
        }
    }

    public static <T> T validateNotNull(T obj, String message) {
        if (obj == null)
            throw new ConstraintsViolationException(message);
        return obj;
    }

}
