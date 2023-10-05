package com.durys.jakub.reportsservice.pattern.domain.validators;

import com.durys.jakub.reportsservice.common.exception.FieldCannotBeEmptyException;
import com.durys.jakub.reportsservice.common.exception.handlers.ValidationExceptionHandler;
import com.durys.jakub.reportsservice.pattern.domain.ReportPatternInfo;
import org.apache.commons.lang3.StringUtils;


public class ReportBasicInformationsValidator {

    public void validate(ReportPatternInfo patternInfo, ValidationExceptionHandler handler) {
        if (StringUtils.isEmpty(patternInfo.getName())) {
            handler.add(new FieldCannotBeEmptyException("Name"));
        }
        if (StringUtils.isEmpty(patternInfo.getSubsystem())) {
            handler.add(new FieldCannotBeEmptyException("Subsystem"));
        }
        if (StringUtils.isEmpty(patternInfo.getDescription())) {
            handler.add(new FieldCannotBeEmptyException("Description"));
        }
    }
}
