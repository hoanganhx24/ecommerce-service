package com.hoanganh24.auth.event;

import com.hoanganh24.common.event.CommandPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    private final static String NOTI_EVENT_BINDING_NAME = "notificationPublish-out-0";

    @Autowired
    private StreamBridge streamBridge;

    public boolean notificationPublish(CommandPayload payload) {
        return streamBridge.send(NOTI_EVENT_BINDING_NAME, payload);
    }
}
