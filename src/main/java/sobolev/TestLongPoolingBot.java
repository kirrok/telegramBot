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

    private final RemotePointService remotePointService;
    private final UpdatesDownloader updatesDownloader;
    private final Mechanics mechanic;

    private final ArrayList<BotSession> allSessions = new ArrayList<>();
    private final String token;
    private final Executor executor;


    public TestLongPoolingBot(DefaultBotConfig botConfig) {
        this.token = botConfig.getToken();
        this.executor = Executors.newFixedThreadPool(botConfig.getThreadNumber());
        this.remotePointService = new RemotePointService(botConfig.getBaseUrl(), botConfig.getToken());
        this.updatesDownloader = new UpdatesDownloader(remotePointService, allSessions);
        this.mechanic = new Mechanics(allSessions);

        LOGGER.info("Bot successfully configured.");
    }

    public void start() {
        LOGGER.info("Starting bot ...");
        executor.execute(updatesDownloader);
        executor.execute(mechanic);
    }
}