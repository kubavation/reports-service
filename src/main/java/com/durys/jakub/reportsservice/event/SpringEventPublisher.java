package com.durys.jakub.reportsservice.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public <T extends Event> void emit(T event) {
        publisher.publishEvent(event);
    }
}
