package com.durys.jakub.reportsservice.cqrs.configuration;


import com.durys.jakub.reportsservice.cqrs.command.Command;
import com.durys.jakub.reportsservice.cqrs.command.CommandHandler;
import com.durys.jakub.reportsservice.cqrs.query.Query;
import com.durys.jakub.reportsservice.cqrs.query.QueryHandler;
import com.durys.jakub.reportsservice.cqrs.query.QueryHandlerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class SpringQueryHandlerProvider implements QueryHandlerProvider {

    private final ConfigurableListableBeanFactory factory;
    private final Map<Class<? extends Query>, String> handlers;

    public SpringQueryHandlerProvider(ConfigurableListableBeanFactory factory) {
        this.factory = factory;
        this.handlers = initialize();
    }

    @Override
    public <T extends Query<R>, R> QueryHandler<T, R> queryHandlerFor(T query) {
        return factory.getBean(handlers.get(query.getClass()), QueryHandler.class);
    }


    private Map<Class<? extends Query>, String> initialize() {

        return factory.getBeansOfType(QueryHandler.class)
                .entrySet()
                .stream()
                .map(entry -> Map.entry(findQueryFrom(entry.getValue().getClass()), entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Class<? extends Query> findQueryFrom(Class<? extends QueryHandler> queryHandler) {
        return Arrays.stream(queryHandler.getGenericInterfaces())
                .filter(ParameterizedType.class::isInstance)
                .map(type -> (Class<? extends Query>) ((ParameterizedType) type).getActualTypeArguments()[0])
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
