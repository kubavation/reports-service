package com.durys.jakub.reportsservice.bundles.infrastructure.query.handler;

import com.durys.jakub.reportsservice.bundles.domain.ReportBundleRepository;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.FindReportBundlesQuery;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.model.ReportBundleDTO;
import com.durys.jakub.reportsservice.cqrs.query.QueryHandler;
import com.durys.jakub.reportsservice.cqrs.query.QueryHandling;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@QueryHandling
public class FindReportBundlesQueryHandler implements QueryHandler<FindReportBundlesQuery, Set<ReportBundleDTO>> {

    private final ReportBundleRepository reportBundleRepository;


    public Set<ReportBundleDTO> handle(FindReportBundlesQuery query) {
        return reportBundleRepository.all();
    }
}
