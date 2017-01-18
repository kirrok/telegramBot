import org.apache.log4j.BasicConfigurator;


/**
 * Created by kirrok on 17.01.17.
 */
public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        final BotConfig botConfig = new BotConfig.Builder()
                .setToken("249321483:AAGeX2H9tgtnSQ3HjAtn__RTB6ftPl0lPYI")
                .setThreadNumber(5)
                .build();

        final TestLongPoolingBot testLongPoolingBot = new TestLongPoolingBot(botConfig);

        testLongPoolingBot.start();

    }
}
