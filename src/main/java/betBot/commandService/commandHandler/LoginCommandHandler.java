package betBot.commandService.commandHandler;

import betBot.model.Message;
import betBot.model.User;
import betBot.service.TelegramApiClient;
import betBot.user.UserRole;
import betBot.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginCommandHandler implements CommandHandler {

    private final UserService userService;
    private final TelegramApiClient telegramApiClient;

    @Autowired
    public LoginCommandHandler(UserService userService, TelegramApiClient telegramApiClient) {
        this.userService = userService;
        this.telegramApiClient = telegramApiClient;
    }

    @Override
    public void handle(Message command) {
        String[] split = command.getText().split(" ");
        User user = userService.getUser(command.getUser().getId());
        if (split.length > 1) {
            String login = split[1];
            userService.login(user, login);
        } else {
            telegramApiClient.sendMessage("Повторите команду с логином. Пример: /login <вашлогин>", user);
        }
    }

}