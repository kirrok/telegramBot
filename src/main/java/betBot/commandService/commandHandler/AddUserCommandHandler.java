package betBot.commandService.commandHandler;

import betBot.model.Message;
import betBot.model.User;
import betBot.service.TelegramApiClient;
import betBot.user.UserRole;
import betBot.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddUserCommandHandler implements CommandHandler {

    private final UserService userService;
    private final TelegramApiClient telegramApiClient;

    @Autowired
    public AddUserCommandHandler(UserService userService, TelegramApiClient telegramApiClient) {
        this.userService = userService;
        this.telegramApiClient = telegramApiClient;
    }

    @Override
    public void handle(Message command) {
        User user = userService.getUser(command.getUser().getId());
        if (user.getRole().equals(UserRole.ADMIN)) {
            String[] split = command.getText().split(" ");
            if (split.length > 1) {
                String newUserLogin = split[1];
                if (!userService.loginIsExist(newUserLogin)) {
                    userService.addUserLogin(newUserLogin);
                    telegramApiClient.sendMessage("Добавлен новый пользователь " + newUserLogin, user);
                } else {
                    String responseMessage = "Такой логин уже существует и ";
                    if (userService.loginIsFree(newUserLogin)) {
                        responseMessage += "свободен";
                    } else {
                        User userByLogin = userService.getUserByLogin(newUserLogin);
                        responseMessage += "занят пользователем: " + userByLogin.getFirstName() + " " + userByLogin.getLastName();
                    }
                    telegramApiClient.sendMessage(responseMessage, user);
                }
            } else {
                telegramApiClient.sendMessage("Повторите команду с логином нового пользователя. Пример: /add_user <логин>", user);
            }
        } else {
            telegramApiClient.sendMessage("Вы должны иметь роль " + UserRole.ADMIN.value() + " для добавления нового пользователя", user);
        }
    }
}
