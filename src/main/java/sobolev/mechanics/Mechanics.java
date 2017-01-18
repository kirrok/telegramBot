package sobolev.mechanics;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sobolev.BotSession;
import sobolev.message.UpdateMessage;
import sobolev.utils.TimeHelper;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by kirrok on 17.01.17.
 */

public class Mechanics implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(Mechanics.class);
    private final ArrayList<BotSession> allSessions;
    private static final int STEP_TIME = 50;

    public Mechanics(ArrayList<BotSession> allSessions) {
        this.allSessions = allSessions;
    }

    @Override
    public void run() {
        LOGGER.info("RUN MECHANICS");
        while (true) {
            final long beginTime = System.currentTimeMillis();
            step();
            final long endTime = System.currentTimeMillis();
            if (endTime - beginTime < STEP_TIME) {
                TimeHelper.goSleep(STEP_TIME - endTime + beginTime);
            }
        }
    }

    private void step() {
        for (BotSession session : allSessions) {
            final Queue<UpdateMessage> updates = session.getUpdates();
            for (UpdateMessage update : updates) {
                LOGGER.info("Sesssion id: " + session.getId() + "   message: " + update.getMessage().getText());
                updates.remove(update);
            }
        }
    }
}