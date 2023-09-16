package com.durys.jakub.reportsservice.event;

public interface EventPublisher {

    <T extends Event> void emit(T t);
}
