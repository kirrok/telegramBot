package betBot.commandService.commandHandler;

import betBot.model.Message;
import betBot.model.User;
import betBot.service.TelegramApiClient;
import betBot.user.UserRole;
import betBot.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LoginListCommandHandler implements CommandHandler {
    private final UserService userService;
    private final TelegramApiClient telegramApiClient;

    @Autowired
    public LoginListCommandHandler(UserService userService, TelegramApiClient telegramApiClient) {
        this.userService = userService;
        this.telegramApiClient = telegramApiClient;
    }

    @Override
    public void handle(Message command) {
        User user = userService.getUser(command.getUser().getId());
        if (user.getRole().equals(UserRole.ADMIN)) {
            Map<String, User> login2UserMap = userService.getLogin2UserMap();
            String response = login2UserMap.entrySet()
                .stream()
                .map(e -> {
                    String loginInfo = e.getKey();
                    User u = e.getValue();
                    if (u != null) {
                        loginInfo += ": " + u.getFirstName() + " " + u.getLastName();
                    } else {
                        loginInfo += ": свободен";
                    }
                    return loginInfo;
                })
                .collect(Collectors.joining("\n", "", ""));
            telegramApiClient.sendMessage(response, user);
        }else {
            telegramApiClient.sendMessage("Необходима роль " + UserRole.ADMIN.value(), user);
        }
    }
}