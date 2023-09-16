package com.durys.jakub.reportsservice.pattern.infrastructure;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import org.springframework.data.repository.CrudRepository;

public interface ReportPatternRepository extends CrudRepository<ReportPattern, Long> {
}
