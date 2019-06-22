package com.revolut.repositories;

import java.util.List;

public interface IRepository <T> {

    T findById(Long id);

    List<T> findAll();

    T create(T domainObject);

    T update(T domainObject);

    boolean delete(Long id);
}
