package com.durys.jakub.reportsservice.generator;

import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

import java.io.InputStream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ReportCache {

    static Try<JasperReport> compiledReport(InputStream patternIS) {
        return Try.of(() -> (JasperReport) JRLoader.loadObject(patternIS));
    }

    static void cache(JasperReport report) {
        try {
            JRSaver.saveObject(report, report.getName());
        } catch (Exception e) {
            log.error("Error caching report", e);
        }
    }

}
