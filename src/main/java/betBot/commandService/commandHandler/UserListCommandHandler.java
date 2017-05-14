package betBot.commandService.commandHandler;

import betBot.model.Message;
import betBot.model.User;
import betBot.service.TelegramApiClient;
import betBot.user.UserRole;
import betBot.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserListCommandHandler implements CommandHandler {
    private final UserService userService;
    private final TelegramApiClient telegramApiClient;

    @Autowired
    public UserListCommandHandler(UserService userService, TelegramApiClient telegramApiClient) {
        this.userService = userService;
        this.telegramApiClient = telegramApiClient;
    }

    @Override
    public void handle(Message command) {
        User user = userService.getUser(command.getUser().getId());
        if (user.getRole().equals(UserRole.ADMIN)) {
            String response = userService.getAll()
                .stream()
                .map(u -> u.getFirstName() + " " + u.getLastName() + " " + u.getRole().value() + " " + u.getLogin())
                .collect(Collectors.joining("\n", "", ""));
            telegramApiClient.sendMessage(response, user);
        } else {
            telegramApiClient.sendMessage("Необходима роль" + UserRole.ADMIN.value(), user);
        }
    }
}
