package com.durys.jakub.reportsservice.notification;

import java.util.UUID;

public interface Notifications {
    void send(UUID receiver, String message);
}
