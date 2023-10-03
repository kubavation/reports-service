package com.durys.jakub.reportsservice.cqrs.query;

public interface QueryHandlerProvider {
    <T extends QueryHandler, R extends Query<?>> T queryHandlerFor(R query);
}
