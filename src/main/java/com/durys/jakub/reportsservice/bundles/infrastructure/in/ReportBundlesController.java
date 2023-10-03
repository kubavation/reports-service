package com.durys.jakub.reportsservice.bundles.infrastructure.in;

import com.durys.jakub.reportsservice.bundles.domain.command.AppendReportsToBundleCommand;
import com.durys.jakub.reportsservice.bundles.domain.command.CreateReportBundleCommand;
import com.durys.jakub.reportsservice.bundles.domain.command.DeleteReportBundleCommand;
import com.durys.jakub.reportsservice.bundles.infrastructure.model.CreateReportBundleDTO;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.FindReportBundles;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.FindReportsInBundleQuery;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.model.ReportBundleDTO;
import com.durys.jakub.reportsservice.bundles.infrastructure.query.model.ReportInBundleDTO;
import com.durys.jakub.reportsservice.cqrs.command.CommandGateway;
import com.durys.jakub.reportsservice.cqrs.query.Queries;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/report-bundles")
@RequiredArgsConstructor
public class ReportBundlesController {

    private final Queries queries;
    private final CommandGateway commandGateway;

    @GetMapping
    Set<ReportBundleDTO> reportBundles() {
        return queries.find(new FindReportBundles());
    }

    @GetMapping("/{bundleId}/reports")
    List<ReportInBundleDTO> reportsInBundle(@PathVariable Long bundleId) {
        return queries.find(new FindReportsInBundleQuery(bundleId));
    }

    @PatchMapping("/{bundleId}/reports")
    void appendToBundle(@PathVariable Long bundleId, @RequestBody Set<Long> reportIds) {
        commandGateway.dispatch(
                new AppendReportsToBundleCommand(bundleId, reportIds));
    }

    @PostMapping
    void create(@RequestBody CreateReportBundleDTO reportBundleDTO) {
        commandGateway.dispatch(
                new CreateReportBundleCommand(reportBundleDTO.getName(), reportBundleDTO.getReportIds())
        );
    }

    @DeleteMapping("/{bundleId}")
    void delete(@PathVariable Long bundleId) {
       commandGateway.dispatch(
               new DeleteReportBundleCommand(bundleId));
    }

}
