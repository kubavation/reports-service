package com.durys.jakub.reportsservice.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RabbitmtqNotificationService implements Notifications {

    private final RabbitTemplate rabbitTemplate;

    @Value("${reports.queue.notifications}")
    private String queue;

    @Override
    public void send(UUID receiver, String message) {
        //todo
        rabbitTemplate.convertAndSend(queue, message);
    }
}
