package com.durys.jakub.reportsservice.bundles.infrastructure.query.handler;

import com.durys.jakub.reportsservice.bundles.domain.ReportBundleRepository;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.FindReportsInBundleQuery;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.model.ReportInBundleDTO;
import com.durys.jakub.reportsservice.cqrs.query.QueryHandler;
import lombok.RequiredArgsConstructor;

import java.util.List;

@QueryHandler
@RequiredArgsConstructor
public class ReportBundlesQueryService {

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
