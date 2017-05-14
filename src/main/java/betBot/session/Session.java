package betBot.session;

import betBot.model.UpdateMessage;
import betBot.model.User;
import lombok.Data;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Data
public class Session {

    private Queue<UpdateMessage> updates = new ConcurrentLinkedQueue<>();
    private final User user;

}