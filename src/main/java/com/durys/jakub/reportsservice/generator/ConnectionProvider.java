package com.durys.jakub.reportsservice.generator;

import com.durys.jakub.reportsservice.pattern.domain.ReportPattern;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
        String url = urlTo(pattern.getInformations().getSubsystem());
        String user = userTo(pattern.getInformations().getSubsystem());
        String password = passwordTo(pattern.getInformations().getSubsystem());

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Invalid DB configuration for subsystem %s".formatted(pattern.getInformations().getSubsystem()));
        }
    }

    private String urlTo(String subsystem) {
        String url = environment.getProperty("%s.db.url".formatted(subsystem));
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("Invalid DB url configuration for subsystem %s".formatted(subsystem));
        }
        return url;
    }

    private String userTo(String subsystem) {
        String user = environment.getProperty("%s.db.user".formatted(subsystem));
        if (StringUtils.isEmpty(user)) {
            throw new RuntimeException("Invalid DB user configuration for subsystem %s".formatted(subsystem));
        }
        return user;
    }

    private String passwordTo(String subsystem) {
        String password = environment.getProperty("%s.db.password".formatted(subsystem));
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("Invalid DB password configuration for subsystem %s".formatted(subsystem));
        }
        return password;
    }
}
