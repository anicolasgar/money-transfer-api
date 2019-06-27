package com.revolut.repositories;

public interface IRepository<T, DTO> {

    T findById(Long id);

    T create(DTO domainObject);

    boolean delete(Long id);
}
