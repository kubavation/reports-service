package com.durys.jakub.reportsservice.common.generator;

import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;

import java.io.InputStream;
import java.nio.file.Path;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ReportCache {

    static Try<JasperReport> compiledReport(Path path) {
        return Try.of(() -> (JasperReport) JRLoader.loadObject(path.toFile()));
    }

    static void cache(JasperReport report) {

        log.info("caching report {}", report.getName());

        try {
            JRSaver.saveObject(report, report.getName());
        } catch (Exception e) {
            log.error("Error caching report", e);
        }
    }

}
