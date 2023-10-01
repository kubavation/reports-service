package com.durys.jakub.reportsservice.cqrs.command;

public interface CommandHandler<T extends Command<R>, R> {
    R handle(T command);
}
