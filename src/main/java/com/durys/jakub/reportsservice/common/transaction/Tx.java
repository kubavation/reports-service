package com.durys.jakub.reportsservice.common.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class Tx {

    private final TransactionTemplate transactionTemplate;

    public Tx(PlatformTransactionManager platformTransactionManager) {
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    public <T> T execute(TransactionCallback<T> action) {
        return transactionTemplate.execute(action);
    }
}
