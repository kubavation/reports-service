package com.durys.jakub.reportsservice.cqrs.command;

public interface CommandHandlerProvider {
    <T extends Command, R> CommandHandler<T, R> commandHandlerOf(T command);
}
