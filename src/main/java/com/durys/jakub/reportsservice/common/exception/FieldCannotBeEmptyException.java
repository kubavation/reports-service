package com.durys.jakub.reportsservice.common.exception;

public class FieldCannotBeEmptyException extends RuntimeException {

    public FieldCannotBeEmptyException(String field) {
        super("%s cannot be empty".formatted(field));
    }
}
