package com.revolut.service;

import com.revolut.datatransferobject.AccountDTO;
import com.revolut.domain.account.Account;
import com.revolut.exception.ConstraintsViolationException;
import com.revolut.exception.NotFoundException;
import com.revolut.repositories.AccountRepository;

import java.util.List;

public enum AccountService implements IService<Account, AccountDTO> {
    INSTANCE;

    @Override
    public Account findById(Long id) throws NotFoundException {
        return AccountRepository.getInstance().findById(id);
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Account create(AccountDTO account) throws ConstraintsViolationException {
       return AccountRepository.getInstance().create(account);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        AccountRepository.getInstance().delete(id);
    }
}
