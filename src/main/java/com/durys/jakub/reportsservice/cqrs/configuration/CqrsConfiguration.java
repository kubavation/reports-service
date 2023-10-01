package com.durys.jakub.reportsservice.cqrs.configuration;

import com.durys.jakub.reportsservice.cqrs.command.CommandHandlerProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CqrsConfiguration {

    @Bean
    CommandHandlerProvider commandHandlerProvider(ConfigurableListableBeanFactory factory) {
        return new SpringCommandHandlerProvider(factory);
    }
}
