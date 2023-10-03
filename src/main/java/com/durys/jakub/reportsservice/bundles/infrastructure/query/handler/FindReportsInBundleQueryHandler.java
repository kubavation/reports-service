package com.durys.jakub.reportsservice.bundles.infrastructure.query.handler;

import com.durys.jakub.reportsservice.bundles.domain.ReportBundleRepository;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.FindReportsInBundleQuery;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.model.ReportInBundleDTO;
import com.durys.jakub.reportsservice.cqrs.query.QueryHandler;
import com.durys.jakub.reportsservice.cqrs.query.QueryHandling;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@QueryHandling
public class FindReportsInBundleQueryHandler implements QueryHandler<FindReportsInBundleQuery, List<ReportInBundleDTO>> {

    private final ReportBundleRepository reportBundleRepository;


    public List<ReportInBundleDTO> handle(FindReportsInBundleQuery query) {
        return reportBundleRepository.reportsInBundle(query.bundleId())
                .stream()
                .map(r -> new ReportInBundleDTO(
                        r.getId(), r.getFileName(), r.getTitle(), r.getDescription(),
                        r.getPatternInformations().getName(),
                        r.getPatternInformations().getDescription(),
                        r.getPatternInformations().getSubsystem()))
                .toList();
    }
}
