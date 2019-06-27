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
    public synchronized Transaction create(TransactionDTO transactionDTO) throws ConstraintsViolationException, NotFoundException {
        Account debitAccount = AccountRepository.getInstance().findById(transactionDTO.getDebitAccount());
        if (debitAccount == null)
            throw new NotFoundException("account " + transactionDTO.getDebitAccount() + " not found");

        Account creditAccount = AccountRepository.getInstance().findById(transactionDTO.getCreditAccount());
        if (creditAccount == null)
            throw new NotFoundException("account " + transactionDTO.getDebitAccount() + " not found");

        return makeTransaction(debitAccount, creditAccount, transactionDTO);
    }

    private synchronized Transaction makeTransaction(Account debitAccount, Account creditAccount, TransactionDTO transactionDTO) {
        final Lock debitLock = debitAccount.writeLock();
        final Lock creditLock = creditAccount.writeLock();
        Long waitInterval = Config.getLong("transactions.lock.wait.interval");

        try {
            boolean resourceAvailable = debitLock.tryLock(waitInterval, TimeUnit.MILLISECONDS) && creditLock.tryLock(waitInterval, TimeUnit.MILLISECONDS);
            if (resourceAvailable) {
                if (debitAccount.debit(transactionDTO.getAmount())) {
                    creditAccount.credit(transactionDTO.getAmount());
                    transactionDTO.setState(TransactionState.COMPLETED);
                } else {
                    transactionDTO.setState(TransactionState.INSUFFICIENT_FUNDS);
                }
            } else {
                transactionDTO.setState(TransactionState.CONCURRENCY_ERROR);
            }

        } catch (InterruptedException e) {
            transactionDTO.setState(TransactionState.CONCURRENCY_ERROR);
            logger.error(e.getLocalizedMessage(), e);
        } finally {
            debitLock.unlock();
            creditLock.unlock();
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
