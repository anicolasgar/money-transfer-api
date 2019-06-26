package com.revolut.datatransferobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revolut.enums.TransactionState;

import java.math.BigDecimal;

public class TransactionDTO {
    private Long id;
    @JsonProperty("debit_account")
    private Long debitAccount;
    @JsonProperty("credit_account")
    private Long creditAccount;
    private BigDecimal amount;
    private TransactionState state;

    public TransactionDTO(Long id, Long debitAccount, Long creditAccount, BigDecimal amount, TransactionState state) {
        this.id = id;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
        this.state = state;
    }

    public TransactionDTO(Long debitAccount, Long creditAccount, BigDecimal amount) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
    }

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
