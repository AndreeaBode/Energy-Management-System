package sd.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatMessage {

    String message;
    String user;
    boolean isTyping;

    public ChatMessage() {

    }
}
