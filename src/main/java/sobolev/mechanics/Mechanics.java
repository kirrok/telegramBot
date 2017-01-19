package sobolev.mechanics;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sobolev.BotSession;
import sobolev.message.UpdateMessage;
import sobolev.service.RemotePointService;
import sobolev.utils.TimeHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

/**
 * Created by kirrok on 17.01.17.
 */

public class Mechanics implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(Mechanics.class);
    private final ArrayList<BotSession> allSessions;
    private static final int STEP_TIME = 50;
    private final RemotePointService remotePointService;


    public Mechanics(ArrayList<BotSession> allSessions, RemotePointService remotePointService) {
        this.allSessions = allSessions;
        this.remotePointService = remotePointService;
    }

    @Override
    public void run() {
        LOGGER.info("RUN MECHANICS");
        while (true) {
            final long beginTime = System.currentTimeMillis();
            step();
            final long endTime = System.currentTimeMillis();
            if (endTime - beginTime < STEP_TIME) {
                TimeHelper.goSleep(STEP_TIME - endTime + beginTime);
            }
        }
    }

    private void step() {
        for (BotSession session : allSessions) {
            final Queue<UpdateMessage> updates = session.getUpdates();
            for (UpdateMessage update : updates) {
                final String message = parseMessageAndSendForAll(update);
                LOGGER.info("Sesssion id: " + session.getId() + "   message: " + message.toString());
                updates.remove(update);

            }
        }
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

        for (BotSession sessionToSendMsg : allSessions) {
            final HashMap<String, String> keyToValueParams = new HashMap<>();
            keyToValueParams.put("chat_id", String.valueOf(sessionToSendMsg.getId()));
            keyToValueParams.put("text", messageToSendBuilder.toString());

            if (sessionToSendMsg.getId() != update.getMessage().getChat().getId()) {
                try {
                    remotePointService.sendGet("sendMessage", keyToValueParams);
                } catch (IOException e) {
                    LOGGER.warn("Error while sending message to session, {}", e);
                }
            }

        }
        return messageToSendBuilder.toString();
    }
}