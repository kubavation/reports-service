package com.durys.jakub.reportsservice.scheduling.event;

import com.durys.jakub.reportsservice.event.Event;

public record GenerateScheduledReportEvent(Long reportId) implements Event {
}
