package com.durys.jakub.reportsservice.report.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Embeddable
public class ReportCreationStatus {

    public enum Status {
        NEW,
        CANCELLED,
        IN_PROGRESS,
        FAILURE,
        SUCCESS
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime at;

}
