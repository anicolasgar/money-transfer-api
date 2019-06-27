package com.revolut.repositories;

import com.revolut.datatransferobject.TransactionDTO;
import com.revolut.domain.transaction.Transaction;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionRepository implements IRepository<Transaction, TransactionDTO> {
    private final ConcurrentMap<Long, Transaction> transactions;
    private static AtomicInteger counter;
    private static TransactionRepository instance = null;

    public static TransactionRepository getInstance() {
        if (instance == null) {
            instance = new TransactionRepository(new ConcurrentHashMap<>());
            counter = new AtomicInteger(0);
        }
        return instance;
    }

    private TransactionRepository(ConcurrentMap<Long, Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Transaction findById(Long id) {
        return this.transactions.get(id);
    }

    @Override
    public Transaction create(TransactionDTO transactionDTO) {
        transactionDTO.setId((long) counter.incrementAndGet());
        Transaction transactionDO = Transaction.make(transactionDTO);
        this.transactions.put(transactionDO.getId(), transactionDO);
        return transactionDO;
    }

    @Override
    public boolean delete(Long id) {
        return this.transactions.remove(id) != null;
    }


}
