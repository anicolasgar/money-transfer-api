package com.revolut.unit;

import com.revolut.domain.account.Account;
import com.revolut.enums.Currency;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {


    @Test
    void testAccountWithNullIdShouldThrowException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Account(null, Currency.USD, true, new BigDecimal(5)));
        assertEquals("id cannot be null", exception.getMessage());
    }

    @Test
    void testAccountWithNullCurrencyShouldThrowException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Account(1L, null, true, new BigDecimal(5)));
        assertEquals("currency cannot be null", exception.getMessage());
    }

    @Test
    void testAccountWithNullBalanceShouldThrowException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Account(1L, Currency.USD, true, null));
        assertEquals("balance cannot be null", exception.getMessage());
    }

    @Test
    void testAccountWithNegativeBalanceShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Account(1L, Currency.USD, true, new BigDecimal(-5)));
        assertEquals("amount cannot be negative", exception.getMessage());
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

        NullPointerException exception = assertThrows(NullPointerException.class, () -> account.debit(null));
        assertEquals("amount cannot be null", exception.getMessage());
    }

    @Test
    void testCreditValueWithNullValueShouldThrowException() {
        Account account = new Account(1L, Currency.USD, true, new BigDecimal(5));
        assertEquals(new BigDecimal(5), account.getBalance());

        NullPointerException exception = assertThrows(NullPointerException.class, () -> account.credit(null));
        assertEquals("amount cannot be null", exception.getMessage());
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
