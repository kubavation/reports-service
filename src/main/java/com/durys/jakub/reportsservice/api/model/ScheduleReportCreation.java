package com.durys.jakub.reportsservice.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleReportCreation extends ReportCreation {
    private LocalDateTime at;
}
