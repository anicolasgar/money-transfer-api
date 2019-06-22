package com.revolut.datatransferobject;

import com.revolut.domain.account.Account;
import com.revolut.enums.TransactionState;

import java.math.BigDecimal;

public class TransactionDTO {
    private Long id;
    private Long debitAccount;
    private Long creditAccount;
    private BigDecimal amount;
    private TransactionState state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(Long debitAccount) {
        this.debitAccount = debitAccount;
    }

    public Long getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(Long creditAccount) {
        this.creditAccount = creditAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }
}
