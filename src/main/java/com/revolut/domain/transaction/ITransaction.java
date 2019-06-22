package com.revolut.domain.transaction;

import com.revolut.domain.Identifiable;
import com.revolut.domain.account.Account;

import java.math.BigDecimal;

public interface ITransaction extends Identifiable {
    Long getDebitAccount();

    Long getCreditAccount();

    BigDecimal getAmount();
}
