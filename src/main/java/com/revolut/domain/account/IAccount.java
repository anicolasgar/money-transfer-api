package com.revolut.domain.account;

import com.revolut.domain.Identifiable;
import com.revolut.enums.Currency;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;

public interface IAccount extends Identifiable {

    Currency getCurrency();

    BigDecimal getBalance();

    boolean debit(BigDecimal amount);

    boolean credit(BigDecimal amount);

    boolean isActive();

    Lock writeLock();
}
