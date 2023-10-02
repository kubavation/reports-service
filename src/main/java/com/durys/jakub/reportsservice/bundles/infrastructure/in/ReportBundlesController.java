package com.durys.jakub.reportsservice.bundles.infrastructure.in;

import com.durys.jakub.reportsservice.bundles.application.ReportBundlesApplicationService;
import com.durys.jakub.reportsservice.bundles.domain.ReportBundleRepository;
import com.durys.jakub.reportsservice.bundles.domain.command.AppendReportsToBundleCommand;
import com.durys.jakub.reportsservice.bundles.infrastructure.model.CreateReportBundleDTO;
import com.durys.jakub.reportsservice.bundles.infrastructure.model.ReportBundleDTO;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.FindReportsInBundleQuery;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.handler.ReportBundlesQueryService;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.model.ReportInBundleDTO;
import com.durys.jakub.reportsservice.cqrs.command.CommandGateway;
import com.durys.jakub.reportsservice.report.domain.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/report-bundles")
@RequiredArgsConstructor
public class ReportBundlesController {

    private final ReportBundlesApplicationService reportBundlesApplicationService;
    private final ReportBundleRepository reportBundleRepository;
    private final ReportBundlesQueryService reportBundlesQueryService;
    private final CommandGateway commandGateway;

    @GetMapping
    Set<ReportBundleDTO> reportBundles() {
        return reportBundleRepository.all();
    }

    @GetMapping("/{bundleId}/reports")
    List<ReportInBundleDTO> reportsInBundle(@PathVariable Long bundleId) {
        return reportBundlesQueryService.handle(new FindReportsInBundleQuery(bundleId));
    }

    @PatchMapping("/{bundleId}/reports")
    void appendToBundle(@PathVariable Long bundleId, @RequestBody Set<Long> reportIds) {
        commandGateway.dispatch(
                new AppendReportsToBundleCommand(bundleId, reportIds));
    }

    @PostMapping
    void create(@RequestBody CreateReportBundleDTO reportBundleDTO) {
        reportBundlesApplicationService.create(reportBundleDTO);
    }

    @DeleteMapping("/{bundleId}")
    void delete(@PathVariable Long bundleId) {
       reportBundlesApplicationService.delete(bundleId);
    }

}
