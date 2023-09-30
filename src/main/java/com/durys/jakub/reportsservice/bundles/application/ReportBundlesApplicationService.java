package com.durys.jakub.reportsservice.bundles.application;

import com.durys.jakub.reportsservice.bundles.domain.ReportBundle;
import com.durys.jakub.reportsservice.bundles.domain.ReportBundleRepository;
import com.durys.jakub.reportsservice.bundles.infrastructure.model.CreateReportBundleDTO;
import com.durys.jakub.reportsservice.report.domain.Report;
import com.durys.jakub.reportsservice.report.domain.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;

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

        ReportBundle reportBundle = new ReportBundle(bundle.getName(), new HashSet<>(reports));

        reportBundleRepository.save(reportBundle);
    }


}
