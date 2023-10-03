package com.durys.jakub.reportsservice.cqrs.configuration;

import com.durys.jakub.reportsservice.cqrs.query.Queries;
import com.durys.jakub.reportsservice.cqrs.query.Query;
import com.durys.jakub.reportsservice.cqrs.query.QueryHandler;
import com.durys.jakub.reportsservice.cqrs.query.QueryHandlerProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpringQueryGateway implements Queries {

    private final QueryHandlerProvider queryHandlerProvider;

    @Override
    public <T extends Query<R>, R> R find(T query) {
        return (R) queryHandlerProvider.queryHandlerFor(query).handle(query);
    }
}
