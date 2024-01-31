package sd.entities;

import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;
}
