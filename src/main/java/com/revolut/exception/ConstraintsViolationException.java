package com.revolut.exception;


public class ConstraintsViolationException extends BadRequestException
{
    public ConstraintsViolationException(String message)
    {
        super(message);
    }
}
