package com.durys.jakub.reportsservice.bundles.infrastructure.in;

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

    @GetMapping
    Set<ReportBundleDTO> reportBundles() {
        return Collections.emptySet(); //todo
    }

    @GetMapping("/{bundleId}/reports")
    Set<Report> reportsInBundle(@PathVariable Long bundleId) {
        return Collections.emptySet(); //todo
    }

    @PatchMapping("/{bundleId}/reports")
    void appendToBundle(@RequestBody Set<Long> reportIds) {
        //todo
    }

    @PostMapping
    void create(@RequestBody CreateReportBundleDTO reportBundleDTO) {
        //todo
    }

    @DeleteMapping("/{bundleId}")
    void delete(@PathVariable Long bundleId) {
        //todo
    }

}
