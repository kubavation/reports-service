package com.durys.jakub.reportsservice.pattern.domain.validators;

import com.durys.jakub.reportsservice.common.exception.FieldCannotBeEmptyException;
import com.durys.jakub.reportsservice.common.exception.handlers.ValidationExceptionHandler;
import org.apache.commons.lang3.StringUtils;


public class ReportBasicInformationsValidator {

    public void validate(String name, String description, String subsystem, ValidationExceptionHandler handler) {
        if (StringUtils.isEmpty(name)) {
            handler.add(new FieldCannotBeEmptyException("Name"));
        }
        if (StringUtils.isEmpty(subsystem)) {
            handler.add(new FieldCannotBeEmptyException("Subsystem"));
        }
        if (StringUtils.isEmpty(description)) {
            handler.add(new FieldCannotBeEmptyException("Description"));
        }
    }

}
