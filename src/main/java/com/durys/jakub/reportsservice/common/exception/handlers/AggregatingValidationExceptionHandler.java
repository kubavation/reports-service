package com.durys.jakub.reportsservice.common.exception.handlers;

import jakarta.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class AggregatingValidationExceptionHandler implements ValidationExceptionHandler {

    private final List<RuntimeException> exceptions = new ArrayList<>();

    @Override
    public void add(ValidationException exception) {
        exceptions.add(exception);
    }

    public boolean hasErrors() {
        return !exceptions.isEmpty();
    }

    public List<String> errorMessages() {
        return exceptions.stream()
                .map(Throwable::getMessage)
                .toList();
    }

}
