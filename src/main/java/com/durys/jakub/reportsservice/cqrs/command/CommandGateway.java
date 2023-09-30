package com.durys.jakub.reportsservice.cqrs.command;

public interface CommandGateway {
    <T extends Command, R> R dispatch(T command);
}
