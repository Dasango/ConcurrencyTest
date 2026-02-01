package com.david.cpuHater.persistence.notification;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApplicationScoped
@AllArgsConstructor
@Data
public class MessageObserver {
    private String message;

}
