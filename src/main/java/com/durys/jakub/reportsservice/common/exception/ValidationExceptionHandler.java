package com.durys.jakub.reportsservice.common.exception;

import jakarta.validation.ValidationException;

public interface ValidationExceptionHandler {
    void add(ValidationException exception);
}
