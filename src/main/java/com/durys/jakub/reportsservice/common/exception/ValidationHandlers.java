package com.durys.jakub.reportsservice.common.exception;

import com.durys.jakub.reportsservice.common.exception.handlers.AggregatingValidationExceptionHandler;
import com.durys.jakub.reportsservice.common.exception.handlers.ThrowingValidationExceptionHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationHandlers {

    public static AggregatingValidationExceptionHandler aggregatingValidationExceptionHandler() {
        return new AggregatingValidationExceptionHandler();
    }

    public static ThrowingValidationExceptionHandler throwingValidationExceptionHandler() {
        return new ThrowingValidationExceptionHandler();
    }
}
