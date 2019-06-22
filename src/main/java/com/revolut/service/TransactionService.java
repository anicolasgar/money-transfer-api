package com.revolut.service;


import com.revolut.config.Config;
import com.revolut.datatransferobject.TransactionDTO;
import com.revolut.domain.account.Account;
import com.revolut.domain.transaction.Transaction;
import com.revolut.enums.TransactionState;
import com.revolut.exception.ConstraintsViolationException;
import com.revolut.exception.NotFoundException;
import com.revolut.repositories.AccountRepository;
import com.revolut.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public enum TransactionService implements IService<Transaction, TransactionDTO> {
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);


    @Override
    public Transaction findById(Long id) throws NotFoundException {
        return TransactionRepository.getInstance().findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return null;
    }

    @Override
    public synchronized Transaction create(TransactionDTO transactionDTO) throws ConstraintsViolationException {
        Account debitAccount = AccountRepository.getInstance().findById(transactionDTO.getDebitAccount());
        Account creditAccount = AccountRepository.getInstance().findById(transactionDTO.getCreditAccount());

        final Lock debitLock = debitAccount.writeLock();
        try {
            if (debitLock.tryLock(Config.ACCOUNT_WAIT_INTERVAL, TimeUnit.MILLISECONDS)) {
                try {
                    final Lock creditLock = creditAccount.writeLock();
                    if (creditLock.tryLock(Config.ACCOUNT_WAIT_INTERVAL, TimeUnit.MILLISECONDS)) {
                        try {
                            if (debitAccount.debit(transactionDTO.getAmount())) {
                                if (creditAccount.credit(transactionDTO.getAmount())) {
                                    transactionDTO.setState(TransactionState.COMPLETED);
                                }
                            }
                            transactionDTO.setState(TransactionState.INSUFFICIENT_FUNDS);
                        } finally {
                            creditLock.unlock();
                        }
                    } else {
                        transactionDTO.setState(TransactionState.CONCURRENCY_ERROR);
                    }
                } finally {
                    debitLock.unlock();
                }
            } else {
                transactionDTO.setState(TransactionState.CONCURRENCY_ERROR);
            }
        } catch (InterruptedException e) {
            transactionDTO.setState(TransactionState.CONCURRENCY_ERROR);
            logger.error(e.getLocalizedMessage(), e);
        }
        Transaction transaction = TransactionRepository.getInstance().create(transactionDTO);
        logger.trace("Transaction {} completed", transaction.getId());
        return transaction;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        TransactionRepository.getInstance().delete(id);
    }
}
