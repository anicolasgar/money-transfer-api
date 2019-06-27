package com.revolut.domain.account;

import com.revolut.config.Config;
import com.revolut.datatransferobject.AccountDTO;
import com.revolut.enums.Currency;
import com.revolut.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account implements IAccount {

    private static final Logger logger = LoggerFactory.getLogger(Account.class);
    private final Long id;
    private final Currency currency;
    private final boolean active;
    private BigDecimal balance;
    private final transient Lock lock;

    public Account(Long id, Currency currency,
                   boolean active, BigDecimal balance) {
        Validator.validateNotNull(id, "id cannot be null");
        Validator.validateNotNull(currency, "currency cannot be null");
        Validator.validateNotNull(balance, "balance cannot be null");
        Validator.validateNotNegative(balance, "balance");

        this.id = id;
        this.currency = currency;
        this.active = active;
        this.balance = balance;
        this.lock = new ReentrantLock();
    }

    @Override
    public final Long getId() {
        return id;
    }

    @Override
    public final Currency getCurrency() {
        return currency;
    }

    @Override
    public final BigDecimal getBalance() {
        try {
            lock.lock();
            return balance;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean debit(BigDecimal amount) {
        Validator.validateNotNull(amount, "amount cannot be null");
        Validator.validateNotNegative(amount, "amount");

        try {
            if (lock.tryLock(Config.getLong("transactions.lock.wait.interval"), TimeUnit.MILLISECONDS)) {
                try {
                    if (balance.compareTo(amount) > 0) {
                        balance = balance.subtract(amount);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    @Override
    public boolean credit(BigDecimal amount) {
        Validator.validateNotNull(amount, "amount cannot be null");
        Validator.validateNotNegative(amount, "amount");

        try {
            if (lock.tryLock(Config.getLong("transactions.lock.wait.interval"), TimeUnit.MILLISECONDS)) {
                try {
                    balance = balance.add(amount);
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public Lock writeLock() {
        return lock;
    }

    @Override
    public String toString() {
        return String.format("Account{id=%d, currency=%s, active=%s, balance=%s}",
                id, currency, active, balance);
    }

    public static Account make(AccountDTO accountDTO) {
        return new Account(accountDTO.getId(), accountDTO.getCurrency(), accountDTO.isActive(), accountDTO.getBalance());
    }

}
