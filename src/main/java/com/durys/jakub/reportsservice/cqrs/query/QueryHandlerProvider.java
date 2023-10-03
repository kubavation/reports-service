package com.durys.jakub.reportsservice.cqrs.query;

public interface QueryHandlerProvider {
    <T extends Query<R>, R>  QueryHandler<T, R> queryHandlerFor(R query);
}
