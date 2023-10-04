package com.durys.jakub.reportsservice.pattern.domain.validators;

import com.durys.jakub.reportsservice.common.exception.FieldCannotBeEmptyException;
import com.durys.jakub.reportsservice.common.exception.handlers.ValidationExceptionHandler;
import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import org.apache.commons.lang3.StringUtils;

public class ReportBasicInformationsValidator {

    public void validate(ReportPattern pattern, ValidationExceptionHandler handler) {
        if (StringUtils.isEmpty(pattern.name())) {
            handler.add(new FieldCannotBeEmptyException("Name"));
        }
        if (StringUtils.isEmpty(pattern.subsystem())) {
            handler.add(new FieldCannotBeEmptyException("Subsystem"));
        }
        if (StringUtils.isEmpty(pattern.description())) {
            handler.add(new FieldCannotBeEmptyException("Description"));
        }
    }
}
