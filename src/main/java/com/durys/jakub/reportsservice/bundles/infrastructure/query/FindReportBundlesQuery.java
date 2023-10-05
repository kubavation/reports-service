package com.durys.jakub.reportsservice.bundles.infrastructure.query;

import com.durys.jakub.reportsservice.bundles.infrastructure.query.model.ReportBundleDTO;
import com.durys.jakub.reportsservice.cqrs.query.Query;

import java.util.Set;

public record FindReportBundlesQuery() implements Query<Set<ReportBundleDTO>> {
}
