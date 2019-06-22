package com.revolut.service;

import com.revolut.exception.ConstraintsViolationException;
import com.revolut.exception.NotFoundException;

import java.util.List;

public interface IService<DO, DTO> {

    DO findById(Long id) throws NotFoundException;

    List<DO> findAll();

    DO create(DTO domainObject) throws ConstraintsViolationException;

    void delete(Long id) throws NotFoundException;
}
