package com.durys.jakub.reportsservice.report.domain.event;

import com.durys.jakub.reportsservice.event.Event;

public record ScheduledReportFaulitilyGeneratedEvent(Long reportId) implements Event {
}
