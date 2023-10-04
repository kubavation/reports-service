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
public class ReportCreationStatus {

    public enum Status {
        WAITING,
        IN_PROGRESS,
        FAILURE,
        SUCCESS,
        DECLINED
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime at;

    public static ReportCreationStatus valueOf(Status status, LocalDateTime at) {
        return new ReportCreationStatus(status, at);
    }

}
