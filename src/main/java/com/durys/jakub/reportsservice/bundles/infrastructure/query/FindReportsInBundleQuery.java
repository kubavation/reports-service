package com.durys.jakub.reportsservice.bundles.infrastructure.query;

import com.durys.jakub.reportsservice.bundles.infrastructure.query.model.ReportInBundleDTO;
import com.durys.jakub.reportsservice.cqrs.query.Query;

import java.util.List;

public record FindReportsInBundleQuery(Long bundleId) implements Query<List<ReportInBundleDTO>> {
}
