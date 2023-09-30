package com.durys.jakub.reportsservice.bundles.infrastructure.in;

import com.durys.jakub.reportsservice.bundles.application.ReportBundlesApplicationService;
import com.durys.jakub.reportsservice.bundles.domain.ReportBundleRepository;
import com.durys.jakub.reportsservice.bundles.infrastructure.model.CreateReportBundleDTO;
import com.durys.jakub.reportsservice.bundles.infrastructure.model.ReportBundleDTO;
import com.durys.jakub.reportsservice.report.domain.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/report-bundles")
@RequiredArgsConstructor
public class ReportBundlesController {

    private final ReportBundlesApplicationService reportBundlesApplicationService;
    private final ReportBundleRepository reportBundleRepository;

    @GetMapping
    Set<ReportBundleDTO> reportBundles() {
        return reportBundleRepository.all();
    }

    @GetMapping("/{bundleId}/reports")
    Set<Report> reportsInBundle(@PathVariable Long bundleId) {
        return Collections.emptySet(); //todo
    }

    @PatchMapping("/{bundleId}/reports")
    void appendToBundle(@PathVariable Long bundleId, @RequestBody Set<Long> reportIds) {
        reportBundlesApplicationService.append(bundleId, reportIds);
    }

    @PostMapping
    void create(@RequestBody CreateReportBundleDTO reportBundleDTO) {
        reportBundlesApplicationService.create(reportBundleDTO);
    }

    @DeleteMapping("/{bundleId}")
    void delete(@PathVariable Long bundleId) {
        //todo
    }

}
