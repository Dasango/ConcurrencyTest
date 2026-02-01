package com.david.cpuHater.persistence.notification;

import jakarta.enterprise.event.Observes;

public class DBNotifications {

    private void inNotification(@Observes MessageObserver messageObserver){
        System.out.println(messageObserver.toString());
    }
}
