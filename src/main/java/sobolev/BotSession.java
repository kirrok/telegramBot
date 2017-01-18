package sobolev;

import sobolev.message.UpdateMessage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by kirrok on 17.01.17.
 */
public class BotSession {
    private final Integer id;
    private Queue<UpdateMessage> updates = new ConcurrentLinkedQueue<>();

    public Integer getId() {
        return id;
    }

    public void addUpdate(UpdateMessage update) {
        updates.add(update);
    }

    public Queue<UpdateMessage> getUpdates() {
        return updates;
    }

    public BotSession(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BotSession session = (BotSession) o;

        if (id != null ? !id.equals(session.id) : session.id != null) return false;
        return updates != null ? updates.equals(session.updates) : session.updates == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (updates != null ? updates.hashCode() : 0);
        return result;
    }
}
