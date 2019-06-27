package com.revolut.unit;

import com.revolut.domain.account.Account;
import com.revolut.enums.Currency;
import com.revolut.exception.ConstraintsViolationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {


    @Test
    void testAccountWithNullIdShouldThrowException() {
        ConstraintsViolationException exception = assertThrows(ConstraintsViolationException.class, () -> new Account(null, Currency.USD, true, new BigDecimal(5)));
        assertEquals("id cannot be null", exception.getDescription());
    }

    @Test
    void testAccountWithNullCurrencyShouldThrowException() {
        ConstraintsViolationException exception = assertThrows(ConstraintsViolationException.class, () -> new Account(1L, null, true, new BigDecimal(5)));
        assertEquals("currency cannot be null", exception.getDescription());
    }

    @Test
    void testAccountWithNullBalanceShouldThrowException() {
        ConstraintsViolationException exception = assertThrows(ConstraintsViolationException.class, () -> new Account(1L, Currency.USD, true, null));
        assertEquals("balance cannot be null", exception.getDescription());
    }

    @Test
    void testAccountWithNegativeBalanceShouldThrowException() {
        ConstraintsViolationException exception = assertThrows(ConstraintsViolationException.class, () -> new Account(1L, Currency.USD, true, new BigDecimal(-5)));
        assertEquals("balance cannot be negative", exception.getDescription());
    }

    @Test
    public void testCreateValidAccount() {
        Account account = new Account(1L, Currency.USD, true, new BigDecimal(5));
        assertNotNull(account);
        assertEquals(1, (long) account.getId());
        assertEquals(Currency.USD, account.getCurrency());
        assertEquals(new BigDecimal(5), account.getBalance());
    }

    @Test
    public void testDebitValueWithNullValueShouldThrowException() {
        Account account = new Account(1L, Currency.USD, true, new BigDecimal(5));
        assertEquals(new BigDecimal(5), account.getBalance());

        ConstraintsViolationException exception = assertThrows(ConstraintsViolationException.class, () -> account.debit(null));
        assertEquals("amount cannot be null", exception.getDescription());
    }

    @Test
    void testCreditValueWithNullValueShouldThrowException() {
        Account account = new Account(1L, Currency.USD, true, new BigDecimal(5));
        assertEquals(new BigDecimal(5), account.getBalance());

        ConstraintsViolationException exception = assertThrows(ConstraintsViolationException.class, () -> account.credit(null));
        assertEquals("amount cannot be null", exception.getDescription());
    }

    @Test
    void testCreditValueToAccountSuccessfully() {
        Account account = new Account(1L, Currency.USD, true, new BigDecimal(5));
        assertEquals(new BigDecimal(5), account.getBalance());
        account.credit(new BigDecimal(2));
        assertEquals(new BigDecimal(7), account.getBalance());
    }

    @Test
    void testDebitValueToAccountSuccessfully() {
        Account account = new Account(1L, Currency.USD, true, new BigDecimal(5));
        assertEquals(new BigDecimal(5), account.getBalance());
        boolean result = account.debit(new BigDecimal(2));
        assertTrue(result);
        assertEquals(new BigDecimal(3), account.getBalance());
    }

    @Test
    void testDebitValueGreaterThanCurrentBalanceShouldRollbackTransaction() {
        Account account = new Account(1L, Currency.USD, true, new BigDecimal(5));
        assertEquals(new BigDecimal(5), account.getBalance());
        boolean result = account.debit(new BigDecimal(8));
        assertFalse(result);
        assertEquals(new BigDecimal(5), account.getBalance());
    }

}
