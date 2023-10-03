package com.durys.jakub.reportsservice.cqrs.query;

public interface Queries {
   <T extends Query<R>, R> R find(T query);
}
