package com.durys.jakub.reportsservice.common.exception;

import jakarta.validation.ValidationException;

public class ThrowingValidationExceptionHandler implements ValidationExceptionHandler {

    @Override
    public void add(ValidationException exception) {
        throw exception;
    }
}
