package com.durys.jakub.reportsservice.cqrs.configuration;

import com.durys.jakub.reportsservice.cqrs.command.Command;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandlerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SpringCommandHandlerProvider implements CommandHandlerProvider {

    private final ConfigurableListableBeanFactory factory;

    private final Map<Class<? extends Command>, String> handlers;

    public SpringCommandHandlerProvider(ConfigurableListableBeanFactory factory) {
        this.factory = factory;
        this.handlers = initialize();
    }

    @Override
    public <T extends Command, R> CommandHandler<T, R> commandHandlerOf(T command) {
        return factory.getBean(handlers.get(command.getClass()), CommandHandler.class);
    }

    private Map<Class<? extends Command>, String> initialize() {
        return factory.getBeansOfType(CommandHandler.class)
                .entrySet()
                .stream()
                .map(entry -> Map.entry(findCommand(entry.getValue().getClass()), entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Class<? extends Command> findCommand(Class<? extends CommandHandler> handlerClass) {
        return Stream.of(handlerClass.getGenericInterfaces())
                .filter(ParameterizedType.class::isInstance)
                .map(type -> (Class<? extends Command>) ((ParameterizedType) type).getActualTypeArguments()[0])
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid command handler configuration"));
    }
}
