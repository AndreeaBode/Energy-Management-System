package sd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TypingEvent {
    private String message;
    private String user;
    private boolean isTyping;
}
