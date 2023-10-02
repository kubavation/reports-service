package com.durys.jakub.reportsservice.cqrs.configuration;

import com.durys.jakub.reportsservice.cqrs.command.Command;
import com.durys.jakub.reportsservice.cqrs.command.CommandGateway;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandlerProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpringCommandGateway implements CommandGateway {

    private final CommandHandlerProvider commandHandlerProvider;

    @Override
    public <T extends Command<R>, R> R dispatch(T command) {
        return (R) commandHandlerProvider.commandHandlerOf(command).handle(command);
    }
}
