package com.durys.jakub.reportsservice.bundles.application;

import com.durys.jakub.reportsservice.bundles.domain.ReportBundle;
import com.durys.jakub.reportsservice.bundles.domain.ReportBundleRepository;
import com.durys.jakub.reportsservice.bundles.infrastructure.in.model.CreateReportBundleDTO;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportBundlesApplicationService {

    private final ReportRepository reportRepository;
    private final ReportBundleRepository reportBundleRepository;

    @Transactional
    public void create(CreateReportBundleDTO bundle) {

        log.info("creating report bundle with name {}", bundle.getName());

        List<Report> reports = reportRepository.findAllById(bundle.getReportIds());

        ReportBundle reportBundle = new ReportBundle(bundle.getName(), Set.copyOf(reports));

        reportBundleRepository.save(reportBundle);
    }


    public void delete(Long bundleId) {

        log.info("delete report bundle (ID: {})", bundleId);

        ReportBundle reportBundle = reportBundleRepository.findById(bundleId)
                .map(ReportBundle::markAsDeleted)
                .orElseThrow(RuntimeException::new);

        reportBundleRepository.save(reportBundle);
    }
}
