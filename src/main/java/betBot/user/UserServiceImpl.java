package betBot.user;

import betBot.model.User;
import betBot.service.TelegramApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private HashMap<Long, User> id2UserMap = new HashMap<>();
    private HashMap<String, User> userLogin2User = new HashMap<>();
    private final TelegramApiClient telegramApiClient;
    private final String rootLogin;

    @Autowired
    public UserServiceImpl(TelegramApiClient telegramApiClient, @Value("${bot.security.login}") String rootLogin) {
        this.telegramApiClient = telegramApiClient;
        this.rootLogin = rootLogin;
    }


    @Override
    public void addUser(User user) {
        id2UserMap.put(user.getId(), user);
    }

    @Override
    public User getUser(long id) {
        return id2UserMap.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(id2UserMap.values());
    }

    @Override
    public Map<String, User> getLogin2UserMap() {
        return userLogin2User;
    }

    @Override
    public boolean isAdmin(User user) {
        return rootLogin.equals(user.getLogin());
    }

    @Override
    public void addUserLogin(String login) {
        userLogin2User.put(login, null);
    }

    @Override
    public void login(User user, String login) {
        if (rootLogin.equals(login)) {
            login(user, login, UserRole.ADMIN);
        } else if (loginIsExist(login) && loginIsFree(login)) {
            login(user, login, UserRole.USER);
        } else {
            telegramApiClient.sendMessage("Логин неверный или уже занят. Текущая роль " + user.getRole().value(), user);
        }
    }

    @Override
    public User getUserByLogin(String login) {
        return userLogin2User.values().stream()
            .filter(user -> login.equals(user.getLogin()))
            .findAny()
            .get();
    }

    @Override
    public boolean loginIsExist(String login) {
        return userLogin2User.containsKey(login);
    }

    @Override
    public boolean loginIsFree(String login) {
        return userLogin2User.containsKey(login) && userLogin2User.get(login) == null;
    }

    private void login(User user, String login, UserRole role) {
        User existingUser = userLogin2User.get(login);
        if (existingUser != null) {
            existingUser.setRole(UserRole.NOT_AUTHORIZED);
            userLogin2User.put(existingUser.getLogin(), null);
        }
        user.setRole(role);
        user.setLogin(login);
        userLogin2User.put(login, user);
        telegramApiClient.sendMessage("Ты авторизован как " + role.value(), user);
    }

}
