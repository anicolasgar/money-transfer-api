package com.revolut.repositories;

import java.util.List;

public class AbstractRepository <T> implements IRepository<T> {
    @Override
    public T findById(Long id) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public T create(T domainObject) {
        return null;
    }

    @Override
    public T update(T domainObject) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
