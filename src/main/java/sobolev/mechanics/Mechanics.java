package sobolev.mechanics;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import sobolev.BotSession;
import sobolev.message.UpdateMessage;
import sobolev.service.RemotePointServiceImpl;
import sobolev.utils.TimeHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

@Slf4j
@Component
public class Mechanics {

    private final ArrayList<BotSession> allSessions;
    private final RemotePointServiceImpl remotePointService;

    public Mechanics(ArrayList<BotSession> allSessions, RemotePointServiceImpl remotePointService) {
        this.allSessions = allSessions;
        this.remotePointService = remotePointService;
    }

    public void step() {
        System.out.println("Mechanics.step");
        allSessions.forEach(session -> {
            Queue<UpdateMessage> updates = session.getUpdates();
            updates.forEach(update -> {
                final String message = parseMessageAndSendForAll(update);
                log.info("Session id: " + session.getId() + "   message: " + message);
                updates.remove(update);
            });
        });
    }

    private String parseMessageAndSendForAll(UpdateMessage update) {
        final StringBuilder messageToSendBuilder = new StringBuilder();
        final String firstName = update.getMessage().getFrom().getFirstName();
        if (firstName != null) {
            messageToSendBuilder.append(firstName);
        }
        messageToSendBuilder.append(' ');
        final String lastName = update.getMessage().getFrom().getLastName();
        if (lastName != null) {
            messageToSendBuilder.append(lastName);
        }
        messageToSendBuilder.append(" : ");
        messageToSendBuilder.append(update.getMessage().getText());

        allSessions.forEach(session -> {
            final HashMap<String, String> keyToValueParams = new HashMap<>();
            keyToValueParams.put("chat_id", String.valueOf(session.getId()));
            keyToValueParams.put("text", messageToSendBuilder.toString());

            if (session.getId() != update.getMessage().getChat().getId()) {
                remotePointService.callMethod("/sendMessage", keyToValueParams);
            }
        });

        return messageToSendBuilder.toString();
    }
}