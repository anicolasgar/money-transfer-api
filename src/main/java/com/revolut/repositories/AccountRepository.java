package com.revolut.repositories;

import com.revolut.datatransferobject.AccountDTO;
import com.revolut.domain.account.Account;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountRepository {//extends AbstractRepository<Account> {
    private final ConcurrentMap<Long, Account> accounts;
    private static AtomicInteger counter;
    private static AccountRepository instance = null;


    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository(new ConcurrentHashMap<>());
            counter = new AtomicInteger(0);
        }
        return instance;
    }

    private AccountRepository(ConcurrentMap<Long, Account> accounts) {
        this.accounts = accounts;
    }

    //    @Override
    public Account findById(Long id) {
        return this.accounts.get(id);
    }

    //    @Override
    public Account create(AccountDTO accountDTO) {
        accountDTO.setId((long) counter.incrementAndGet());
        Account accountDO = Account.make(accountDTO);
        this.accounts.put(accountDO.getId(), accountDO);
        return accountDO;
    }

    //    @Override
    public boolean delete(Long id) {
        return this.accounts.remove(id) != null;
    }

}
