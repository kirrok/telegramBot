package sobolev.mechanics;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sobolev.BotSession;
import sobolev.message.ResponseMessage;
import sobolev.message.UpdateMessage;
import sobolev.service.RemotePointServiceImpl;
import sobolev.utils.TimeHelper;

import java.util.*;

@Slf4j
@Component
public class UpdatesDownloadingTask {
    private Integer lastId = null;
    private final ArrayList<BotSession> allSessions;
    private HashMap<Integer, BotSession> mapSessionIdToUpdates = new HashMap<>();

    private RemotePointServiceImpl remotePointService;

    public UpdatesDownloadingTask(ArrayList<BotSession> allSessions, RemotePointServiceImpl remotePointService) {
        this.allSessions = allSessions;
        this.remotePointService = remotePointService;
    }

    public void getUpdates() {
        System.out.println("UpdatesDownloadingTask.getUpdates");
        ResponseMessage<List<UpdateMessage>> response = remotePointService.getUpdates(lastId);

        if (response.isOk()) {
            response.getResult()
                .stream()
                .map(update -> {
                    new ObjectMapper().convertValue(update, UpdateMessage.class);
                    int sessionId = update.getMessage().getFrom().getId();
                    if (!mapSessionIdToUpdates.containsKey(sessionId)) {
                        BotSession botSession = new BotSession(sessionId);
                        allSessions.add(botSession);
                        mapSessionIdToUpdates.put(botSession.getId(), botSession);
                    }
                    mapSessionIdToUpdates.get(sessionId).addUpdate(update);
                    return update.getUpdateId();
                })
                .max(Comparator.comparingInt(o -> o))
                .ifPresent(integer -> lastId = integer + 1);
        } else {
            log.error("Error requesting get updates: {}", response.getDescription());
        }

    }
}


