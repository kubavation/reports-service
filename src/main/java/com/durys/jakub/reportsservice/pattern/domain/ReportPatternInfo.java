package com.durys.jakub.reportsservice.pattern.domain;

import com.durys.jakub.reportsservice.common.exception.ValidationHandlers;
import com.durys.jakub.reportsservice.common.exception.handlers.ValidationExceptionHandler;
import com.durys.jakub.reportsservice.pattern.domain.validators.ReportBasicInformationsValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class ReportPatternInfo {

    @Column(name = "PATTERN_NAME")
    private String name;
    @Column(name = "PATTERN_DESCRIPTION")
    private String description;
    @Column(name = "PATTER_SUBSYSTEM")
    private String subsystem;

    @Column(name = "GENERATION_TYPE")
    @Enumerated(EnumType.STRING)
    private ReportPatternGenerationType generationType;

    public ReportPatternInfo(String name, String description, String subsystem) {
        this(name, description, subsystem, ReportPatternGenerationType.NONE);
    }

    public ReportPatternInfo(String name, String description, String subsystem, ReportPatternGenerationType generationType) {
        this.name = name;
        this.description = description;
        this.subsystem = subsystem;
        this.generationType = generationType;
        test(this, ValidationHandlers.throwingValidationExceptionHandler());
    }

    public boolean dbGeneration() {
        return ReportPatternGenerationType.DB.equals(generationType);
    }


    public static void test(ReportPatternInfo info, ValidationExceptionHandler handler) {
        new ReportBasicInformationsValidator().validate(info, handler);
    }

}
