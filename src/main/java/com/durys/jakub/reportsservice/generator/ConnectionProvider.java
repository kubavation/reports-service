package com.durys.jakub.reportsservice.generator;

import com.durys.jakub.reportsservice.sharedkernel.model.ReportPatternInfo;
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

    public Connection obtainConnection(ReportPatternInfo pattern) {
        String url = urlTo(pattern.getSubsystem(), environment);
        String user = userTo(pattern.getSubsystem(), environment);
        String password = passwordTo(pattern.getSubsystem(), environment);

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Invalid DB configuration for subsystem %s".formatted(pattern.getSubsystem()));
        }
    }

    private static String urlTo(String subsystem, Environment environment) {
        String url = environment.getProperty("%s.db.url".formatted(subsystem));
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("Invalid DB url configuration for subsystem %s".formatted(subsystem));
        }
        return url;
    }

    private static String userTo(String subsystem, Environment environment) {
        String user = environment.getProperty("%s.db.user".formatted(subsystem));
        if (StringUtils.isEmpty(user)) {
            throw new RuntimeException("Invalid DB user configuration for subsystem %s".formatted(subsystem));
        }
        return user;
    }

    private static String passwordTo(String subsystem, Environment environment) {
        String password = environment.getProperty("%s.db.password".formatted(subsystem));
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("Invalid DB password configuration for subsystem %s".formatted(subsystem));
        }
        return password;
    }
}
