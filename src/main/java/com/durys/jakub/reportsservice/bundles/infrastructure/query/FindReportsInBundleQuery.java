package com.durys.jakub.reportsservice.bundles.infrastructure.query;

import com.durys.jakub.reportsservice.cqrs.query.Query;

public record FindReportsInBundleQuery(Long bundleId) implements Query { //todo?
}
