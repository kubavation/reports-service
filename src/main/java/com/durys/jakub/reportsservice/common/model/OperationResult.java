package com.durys.jakub.reportsservice.common.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
public class OperationResult {

    public enum Status {
        SUCCESS,
        FAILURE
    }

    private final Status status;
    private final List<String> errorMessages;

    public OperationResult(@NonNull List<String> errorMessages) {
        this.errorMessages = errorMessages;

        if (errorMessages.isEmpty()) {
            this.status = Status.SUCCESS;
        } else {
            this.status = Status.FAILURE;
        }
    }
}
