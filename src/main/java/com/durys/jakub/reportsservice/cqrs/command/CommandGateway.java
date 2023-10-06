package com.durys.jakub.reportsservice.cqrs.command;

import java.util.List;

public interface CommandGateway {

    <T extends Command<R>, R> R dispatch(T command);

    default <T extends Command<R>, R> List<R> dispatchAll(List<T> commands) {
        return commands.parallelStream()
                .map(this::dispatch)
                .toList();
    }
}
