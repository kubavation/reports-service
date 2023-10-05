package com.durys.jakub.reportsservice.common.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;

@Getter
public class OperationResult {

    public enum Status {
        SUCCESS,
        FAILURE
    }

    private final Status status;
    private final List<String> errorMessages;

    private OperationResult(@NonNull List<String> errorMessages) {
        this.errorMessages = errorMessages;

        if (errorMessages.isEmpty()) {
            this.status = Status.SUCCESS;
        } else {
            this.status = Status.FAILURE;
        }
    }

    public static OperationResult failure(@NonNull List<String> errorMessages) {
        return new OperationResult(errorMessages);
    }

    public static OperationResult success() {
        return new OperationResult(Collections.emptyList());
    }
}
