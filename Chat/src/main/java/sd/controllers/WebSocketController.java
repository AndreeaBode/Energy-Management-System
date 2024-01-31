package sd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import sd.entities.ChatMessage;
import sd.entities.Message;
import sd.entities.TypingEvent;
import sd.services.ChatService;

import java.util.List;

@Controller
public class WebSocketController {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/typing/{roomId}")
    @SendTo("/topic/{roomId}")
    public TypingEvent handleTyping(@DestinationVariable String roomId, TypingEvent typingEvent) {
        System.out.println("Handling typing event for room: " + roomId);
        System.out.println(typingEvent);
        System.out.println(new TypingEvent("typing",typingEvent.getUser(), typingEvent.isTyping()));
        return new TypingEvent("typing",typingEvent.getUser(), typingEvent.isTyping());
    }




    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
   public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message){
        System.out.println(message);
        System.out.println(new ChatMessage(message.getMessage(), message.getUser(), message.isTyping()));

        return new ChatMessage(message.getMessage(), message.getUser(), message.isTyping());
    }


}
