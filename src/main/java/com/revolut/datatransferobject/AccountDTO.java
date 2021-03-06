package com.revolut.datatransferobject;

import com.revolut.enums.Currency;

import java.math.BigDecimal;

public class AccountDTO {
    private Long id;
    private Currency currency;
    private boolean active;
    private BigDecimal balance;

    public AccountDTO(Long id, Currency currency, boolean active, BigDecimal balance) {
        this.id = id;
        this.currency = currency;
        this.active = active;
        this.balance = balance;
    }

    public AccountDTO(Currency currency, boolean active, BigDecimal balance) {
        this.currency = currency;
        this.active = active;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
