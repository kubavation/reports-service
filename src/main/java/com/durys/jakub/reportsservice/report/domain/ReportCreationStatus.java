package com.durys.jakub.reportsservice.report.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
class ReportCreationStatus {

    public enum Status {
        IN_PROGRESS,
        FAILURE,
        SUCCESS
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime at;

}
