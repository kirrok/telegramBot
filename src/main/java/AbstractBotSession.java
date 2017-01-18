/**
 * Created by kirrok on 17.01.17.
 */
public abstract class AbstractBotSession implements Runnable {
    protected static String apiUrl;
    protected String sessionId;

    public AbstractBotSession(String sessionId) {
        this.sessionId = sessionId;
    }
}
