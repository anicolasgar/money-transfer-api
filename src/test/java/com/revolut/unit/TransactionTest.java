package com.revolut.unit;

import com.revolut.domain.transaction.Transaction;
import com.revolut.enums.TransactionState;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {

    @Test
    void testCreateTransactionWithNullIdShouldThrowException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Transaction(null, 2L, 3L, new BigDecimal(5), TransactionState.NEW));
        assertEquals("id cannot be null", exception.getMessage());
    }

    @Test
    void testCreateTransactionWithNullDebitIdShouldThrowException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Transaction(1L, null, 3L, new BigDecimal(5), TransactionState.NEW));
        assertEquals("debit_account cannot be null", exception.getMessage());
    }

    @Test
    void testCreateTransactionWithNullCreditIdShouldThrowException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Transaction(1L, 2L, null, new BigDecimal(5), TransactionState.NEW));
        assertEquals("credit_account cannot be null", exception.getMessage());
    }

    @Test
    void testCreateTransactionWithSameCreditAccountIdAndDebitAccountIdShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Transaction(1L, 2L, 2L, new BigDecimal(5), TransactionState.NEW));
        assertEquals("credit_account and debit_account is the same account", exception.getMessage());
    }

    @Test
    void testCreateTransactionWithNullAmountShouldThrowException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Transaction(1L, 2L, 3L, null, TransactionState.NEW));
        assertEquals("amount cannot be null", exception.getMessage());
    }

    @Test
    void testAccountWithNegativeAmountShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Transaction(1L, 2L, 3L, new BigDecimal(-5), TransactionState.NEW));
        assertEquals("amount cannot be negative", exception.getMessage());
    }

    @Test
    public void testCreateTransactionWithNullTransactionStateShouldThrowException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Transaction(1L, 2L, 3L, new BigDecimal(5), null));
        assertEquals("transaction_state cannot be null", exception.getMessage());
    }

    @Test
    public void testCreateTransactionSuccessfully() {
        Transaction transaction = new Transaction(1L, 2L, 3L, new BigDecimal(5), TransactionState.NEW);
        assertNotNull(transaction);
        assertEquals(1L, (long) transaction.getId());
        assertEquals(2L, (long) transaction.getDebitAccount());
        assertEquals(3L, (long) transaction.getCreditAccount());
        assertEquals(new BigDecimal(5), transaction.getAmount());
        assertEquals(TransactionState.NEW, transaction.getTransactionState());
    }

}
