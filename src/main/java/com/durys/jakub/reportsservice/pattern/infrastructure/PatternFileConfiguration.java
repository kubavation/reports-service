package com.durys.jakub.reportsservice.pattern.infrastructure;

import com.durys.jakub.reportsservice.pattern.domain.FilePatternRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatternFileConfiguration {

    @Bean
    FilePatternRepository filePatternRepository() {
        return new LocalFilePatternRepository();
    }
}
