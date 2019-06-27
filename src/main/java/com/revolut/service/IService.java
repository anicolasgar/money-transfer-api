package com.revolut.service;

import com.revolut.exception.ConstraintsViolationException;
import com.revolut.exception.NotFoundException;

import java.util.List;

public interface IService<DO, DTO> {

    DO findById(Long id) throws NotFoundException;

    DO create(DTO domainObject) throws ConstraintsViolationException, NotFoundException;

    void delete(Long id) throws NotFoundException;
}
