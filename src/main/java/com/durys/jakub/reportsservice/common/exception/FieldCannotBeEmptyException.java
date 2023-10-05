package com.durys.jakub.reportsservice.common.exception;

import jakarta.validation.ValidationException;

public class FieldCannotBeEmptyException extends ValidationException {

    public FieldCannotBeEmptyException(String field) {
        super("%s cannot be empty".formatted(field));
    }
}
