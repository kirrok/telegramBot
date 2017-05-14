package betBot.session;

import betBot.model.User;

import java.util.Collection;

public interface SessionService {

    void processSession(Session session);

    void addSession(Session session);

    Session getSession(User user);

    Collection<Session> getAll();
}
