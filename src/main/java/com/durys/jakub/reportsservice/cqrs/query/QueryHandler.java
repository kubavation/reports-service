package com.durys.jakub.reportsservice.cqrs.query;

public interface QueryHandler<T extends Query<R>, R> {
   R handle(T query);
}
