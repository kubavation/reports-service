package com.durys.jakub.reportsservice.pattern.infrastructure.query;

import com.durys.jakub.reportsservice.cqrs.query.Query;
import com.durys.jakub.reportsservice.pattern.infrastructure.in.model.ReportPatternInfoDTO;

import java.util.Set;

public record FindSubsystemReportPatternsQuery(String subsystem) implements Query<Set<ReportPatternInfoDTO>> {
}
