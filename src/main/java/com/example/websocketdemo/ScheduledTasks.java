package com.example.websocketdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    private MyWebSocketHandler myWebSocketHandler;

    @Scheduled(fixedRate = 2000)
    public void sendPeriodicMessages() {
        String currentTime = java.time.LocalTime.now().toString();
        String message = "Message from server: Current time is " + currentTime;

        myWebSocketHandler.sendMessageToAll(message);
    }
}
