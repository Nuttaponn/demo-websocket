package com.websocket.websocket.controller;

import com.websocket.websocket.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private AtomicBoolean receivedMessage = new AtomicBoolean(false);

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(new Date());
        receivedMessage.set(true);
        return chatMessage;
    }

    @Scheduled(fixedRate = 5000)
    public void checkForInactivity() {
        if (!receivedMessage.getAndSet(false)) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setNickname("System");
            chatMessage.setContent("Do you have any questions?");
            chatMessage.setTimestamp(new Date());
            messagingTemplate.convertAndSend("/topic/messages", chatMessage);
        }
    }
}
