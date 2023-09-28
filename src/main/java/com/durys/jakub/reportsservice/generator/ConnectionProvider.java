package com.durys.jakub.reportsservice.generator;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
class ConnectionProvider {

    private final Environment environment;

    public Connection obtainConnection(ReportPattern pattern) {
        String url = environment.getProperty("%s.db.url".formatted(pattern.getInformations().getSubsystem()));
        String user = environment.getProperty("%s.db.user".formatted(pattern.getInformations().getSubsystem()));
        String password = environment.getProperty("%s.db.password".formatted(pattern.getInformations().getSubsystem()));

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Invalid DB configuration for subsystem %s".formatted(pattern.getInformations().getSubsystem()));
        }
    }
}
