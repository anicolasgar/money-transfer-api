package com.revolut.domain.transaction;

import com.revolut.datatransferobject.TransactionDTO;
import com.revolut.enums.TransactionState;
import com.revolut.domain.account.Account;
import com.revolut.service.TransactionService;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Transaction implements ITransaction {
    private final Long id;
    private final Long debitAccount;
    private final Long creditAccount;
    private final BigDecimal amount;
    private TransactionState state;

    public Transaction(Long id, Long debitAccount, Long creditAccount, BigDecimal amount, TransactionState transactionState) {
        this.id = id;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
        this.state = transactionState;
    }

    @Override
    public Long getDebitAccount() {
        return debitAccount;
    }

    @Override
    public Long getCreditAccount() {
        return creditAccount;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public Long getId() {
        return id;
    }

    public static Transaction make(TransactionDTO transactionDTO) {
        return new Transaction(transactionDTO.getId(), transactionDTO.getDebitAccount(), transactionDTO.getCreditAccount(), transactionDTO.getAmount(), transactionDTO.getState());
    }
}
