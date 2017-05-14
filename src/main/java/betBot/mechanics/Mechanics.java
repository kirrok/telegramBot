package betBot.mechanics;

import betBot.commandService.CommandService;
import betBot.model.Message;
import betBot.session.SessionService;
import lombok.extern.slf4j.Slf4j;
import betBot.session.Session;
import betBot.model.UpdateMessage;
import betBot.service.TelegramApiClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Queue;

@Slf4j
@Service
public class Mechanics {

    private final TelegramApiClientImpl telegramApiClient;
    private final CommandService commandService;
    private final SessionService sessionService;

    @Autowired
    public Mechanics(TelegramApiClientImpl telegramApiClient, CommandService commandService, SessionService sessionService) {
        this.sessionService = sessionService;
        this.telegramApiClient = telegramApiClient;
        this.commandService = commandService;
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
        if (commandService.isCommand(message)) {
            commandService.processCommand(message);
        } else {

        }
    }
}