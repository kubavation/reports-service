package com.durys.jakub.reportsservice.scheduling.infrastructure.in;

import com.durys.jakub.reportsservice.scheduling.infrastructure.ScheduledReportDTO;
import com.durys.jakub.reportsservice.scheduling.domain.ScheduledReportsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/scheduled-reports")
@RequiredArgsConstructor
public class ScheduledReportsController {

    private final ScheduledReportsRepository scheduledReportsRepository;

    @GetMapping
    public List<ScheduledReportDTO> findAllScheduled() {
        return scheduledReportsRepository.findAllScheduled();
    }
}
