package sd.services;

import org.springframework.stereotype.Service;
import sd.entities.Message;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private final List<Message> messages = new ArrayList<>();

    public void sendMessage(Message chatMessage) {
        messages.add(chatMessage);
        // Implement logic to notify administrators about the new message
    }

    public List<Message> getMessages() {
        return messages;
    }
}
