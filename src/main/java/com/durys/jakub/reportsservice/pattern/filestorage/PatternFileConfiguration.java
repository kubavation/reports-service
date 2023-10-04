package com.durys.jakub.reportsservice.pattern.filestorage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatternFileConfiguration {

    @Bean
    FilePatternRepository filePatternRepository() {
        return new LocalFilePatternRepository();
    }
}
