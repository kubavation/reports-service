package com.durys.jakub.reportsservice.bundles.domain;

import com.durys.jakub.reportsservice.bundles.infrastructure.model.ReportBundleDTO;
import com.durys.jakub.reportsservice.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ReportBundleRepository extends JpaRepository<ReportBundle, Long> {

    @Query("select new com.durys.jakub.reportsservice.bundles.infrastructure.model.ReportBundleDTO(r.name) from ReportBundle r")
    Set<ReportBundleDTO> all();

    @Query("select r from Report r join ReportBundle b where b.id = :bundleId")
    Set<Report> reportsInBundle(Long bundleId);

}
