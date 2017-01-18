package sobolev;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by kirrok on 17.01.17.
 */
public class DefaultBotConfig {

    final Properties botProps;

    {
        botProps = new Properties();
        try {
            botProps.load(new FileInputStream(new File("/home/kirrok/workspace/TestBot/src/main/resources/application.properties")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getThreadNumber() {
        return Integer.parseInt(botProps.getProperty("bot.executor.threadNum"));
    }

    public String getToken() {
        return botProps.getProperty("bot.token");
    }

    public String getBaseUrl() {
        return botProps.getProperty("bot.api.url");
    }
}
