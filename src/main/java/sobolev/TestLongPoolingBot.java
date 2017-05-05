package sobolev;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sobolev.mechanics.Mechanics;
import sobolev.mechanics.UpdatesDownloader;
import sobolev.service.RemotePointService;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by kirrok on 17.01.17.
 */
public class TestLongPoolingBot {
    private static final Logger LOGGER = LogManager.getLogger(TestLongPoolingBot.class);

    private final UpdatesDownloader updatesDownloader;
    private final Mechanics mechanic;

    private final Executor executor;


    public TestLongPoolingBot(DefaultBotConfig botConfig) {
        this.executor = Executors.newFixedThreadPool(botConfig.getThreadNumber());
        RemotePointService remotePointService = new RemotePointService(botConfig.getBaseUrl(), botConfig.getToken());
        ArrayList<BotSession> allSessions = new ArrayList<>();
        this.updatesDownloader = new UpdatesDownloader(allSessions, remotePointService);
        this.mechanic = new Mechanics(allSessions, remotePointService);

        LOGGER.info("Bot successfully configured.");
    }

    public void start() {
        LOGGER.info("Starting bot ...");
        executor.execute(updatesDownloader);
        executor.execute(mechanic);
    }
}