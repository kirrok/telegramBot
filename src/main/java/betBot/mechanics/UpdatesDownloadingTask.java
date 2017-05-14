package betBot.mechanics;

import betBot.model.User;
import betBot.session.SessionService;
import betBot.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import betBot.session.Session;
import betBot.model.ResponseMessage;
import betBot.model.UpdateMessage;
import betBot.service.TelegramApiClientImpl;

import java.util.*;

@Slf4j
@Component
public class UpdatesDownloadingTask {

    private Integer lastId = null;
    private final TelegramApiClientImpl remotePointService;
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public UpdatesDownloadingTask(TelegramApiClientImpl remotePointService, UserService userService, SessionService sessionService) {
        this.remotePointService = remotePointService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public void getUpdates() {
        ResponseMessage<List<UpdateMessage>> response = remotePointService.getUpdates(lastId);
        if (response.isOk()) {
            response.getResult()
                .stream()
                .map(update -> {
                    User user = userService.getUser(update.getMessage().getUser().getId());
                    if (user == null) {
                        user = update.getMessage().getUser();
                        userService.addUser(user);
                    }
                    Session session = sessionService.getSession(user);
                    if (session == null) {
                        session = new Session(user);
                        sessionService.addSession(session);
                    }
                    session.getUpdates().add(update);
                    return update.getUpdateId();
                })
                .max(Comparator.comparingInt(o -> o))
                .ifPresent(maxUpdateId -> lastId = maxUpdateId + 1);
        } else {
            log.error("Error requesting get updates: {}", remotePointService.getUpdates(lastId).getDescription());
        }

    }
}