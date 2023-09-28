package com.durys.jakub.reportsservice.report.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r FROM Report r LEFT JOIN FETCH r.parameters  WHERE r.id = :id")
    Optional<Report> find(Long id);
}
