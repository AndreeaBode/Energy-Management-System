package sd;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import sd.entitie.Notification;


@Service
@RequiredArgsConstructor
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendNotification(Notification notification){
        simpMessagingTemplate.convertAndSend("/topic/socket/client", notification);
    }
}
