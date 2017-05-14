package betBot.mechanics;

import betBot.commandService.CommandService;
import betBot.model.Message;
import betBot.model.PhotoSize;
import betBot.model.User;
import betBot.session.SessionService;
import betBot.user.UserService;
import lombok.extern.slf4j.Slf4j;
import betBot.model.UpdateMessage;
import betBot.service.TelegramApiClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.Queue;

@Slf4j
@Service
public class Mechanics {

    private final TelegramApiClientImpl telegramApiClient;
    private final CommandService commandService;
    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    public Mechanics(TelegramApiClientImpl telegramApiClient, CommandService commandService, SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.telegramApiClient = telegramApiClient;
        this.commandService = commandService;
        this.userService = userService;
    }

    public void step() {
        System.out.println("Mechanics.step");
        sessionService.getAll().forEach(session -> {
            Queue<UpdateMessage> updates = session.getUpdates();
            updates.forEach(update -> {
                processUpdate(update);
                updates.remove(update);
            });
        });
    }

    private void processUpdate(UpdateMessage update) {
        Message message = update.getMessage();
        User user = userService.getUser(message.getUser().getId());
        if (commandService.isCommand(message)) {
            commandService.processCommand(message);
        } else {
            if (userService.isAdmin(user)) {
                if (StringUtils.hasText(message.getText())) {
                    sendTextForAllUsersExceptMe(message);
                } else if (!CollectionUtils.isEmpty(message.getPhoto())) {
                    sendPhotoForAllUsersExceptMe(message);
                }
            }
        }
    }

    private void sendTextForAllUsersExceptMe(Message message) {
        User user = message.getUser();
        String text = message.getText();
        String userInfo = user.getFirstName() + " " + user.getLastName();
        userService.getAll()
            .stream()
            .filter(u -> u.getId() != user.getId())
            .forEach(u -> telegramApiClient.sendMessage(userInfo + " : " + text, u));
    }

    private void sendPhotoForAllUsersExceptMe(Message message) {
        User user = message.getUser();
        message.getPhoto()
            .stream()
            .max(Comparator.comparingLong(PhotoSize::getSize))
            .ifPresent(photo -> userService.getAll()
                .stream()
                .filter(u -> u.getId() != user.getId())
                .forEach(u -> telegramApiClient.sendPhoto(photo, u))
            );
    }
}