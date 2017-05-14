package betBot.user;

import betBot.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    void addUser(User user);

    User getUser(long id);

    List<User> getAll();

    Map<String, User> getLogin2UserMap();

    /**
     * Attempts to add new user login. If such login already exists returns status of login.
     *
     * @param login
     * @return false - login is already taken
     * true - login is free
     */
    void addUserLogin(String login);

    void login(User user, String login);

    User getUserByLogin(String login);

    boolean loginIsExist(String login);

    boolean loginIsFree(String login);

}
