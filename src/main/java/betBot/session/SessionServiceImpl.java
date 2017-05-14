package betBot.session;

import betBot.model.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class SessionServiceImpl implements SessionService {

    private final HashMap<User, Session> sessions = new HashMap<>();

    @Override
    public void processSession(Session session) {
    }

    @Override
    public void addSession(Session session) {
        sessions.put(session.getUser(), session);
    }

    @Override
    public Session getSession(User user) {
        return sessions.get(user);
    }

    @Override
    public Collection<Session> getAll() {
        return sessions.values();
    }

}
