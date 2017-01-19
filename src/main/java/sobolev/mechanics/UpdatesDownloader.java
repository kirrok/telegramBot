package sobolev.mechanics;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sobolev.BotSession;
import sobolev.message.SuccessUpdateMessage;
import sobolev.message.UpdateMessage;
import sobolev.service.RemotePointService;
import sobolev.utils.MessageParser;
import sobolev.utils.TimeHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kirrok on 17.01.17.
 */

public class UpdatesDownloader implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(UpdatesDownloader.class);
    private static final int STEP_TIME = 50;

    private final ArrayList<BotSession> allSessions;
    private HashMap<Integer, BotSession> mapSessionIdToUpdates = new HashMap<>();

    private RemotePointService remotePointService;
    private final MessageParser parser = new MessageParser();

    private static final String GET_UPDATES = "getUpdates";
    private static final String OFFSET = "offset";

    public UpdatesDownloader(ArrayList<BotSession> allSessions, RemotePointService remotePointService) {
        this.remotePointService = remotePointService;
        this.allSessions = allSessions;
    }

    @Override
    public void run() {
        LOGGER.info("RUN UPD_DOWNLOADER");
        Integer lasId = null;
        while (true) {
            final long beginTime = System.currentTimeMillis();
            lasId = getUpdates(lasId);
            final long endTime = System.currentTimeMillis();
            if (endTime - beginTime < STEP_TIME) {
                TimeHelper.goSleep(STEP_TIME - endTime + beginTime);
            }
        }
    }

    public void addSession(BotSession botSession) {
        allSessions.add(botSession);
        mapSessionIdToUpdates.put(botSession.getId(), botSession);
    }

    private int getUpdates(Integer lastUpdateId) {
        final HashMap<String, String> keyToValue = new HashMap<>();

        if (lastUpdateId != null) {
            keyToValue.put("offset", (++lastUpdateId).toString());
        }

        SuccessUpdateMessage successUpdateMessage = null;
        try {
            successUpdateMessage = new ObjectMapper().
                    readValue(remotePointService.sendGet(GET_UPDATES, keyToValue), SuccessUpdateMessage.class);
        } catch (IOException e) {
            LOGGER.info("Errow while getResult , {}", e);
        }
        int maxUpdateId = 0;

        if (successUpdateMessage != null) {
            final List<UpdateMessage> updates = successUpdateMessage.getResult();

            for (UpdateMessage update : updates) {
                final int currentUpdateId = update.getUpdateId();
                if (currentUpdateId > maxUpdateId) {
                    maxUpdateId = currentUpdateId;
                }
                final Integer sessionId = update.getMessage().getFrom().getId();
                if (!mapSessionIdToUpdates.containsKey(sessionId)) {
                    addSession(new BotSession(sessionId));
                }
                mapSessionIdToUpdates.get(sessionId).addUpdate(update);
            }
        }
        return maxUpdateId;
    }
}