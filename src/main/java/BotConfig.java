/**
 * Created by kirrok on 17.01.17.
 */
public class BotConfig {
    private static final String BASE_URL = "https://api.telegram.org/bot";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    private String token;
    private int threadNumber = 1;

    private BotConfig(String token, int threadNumber) {
        this.token = token;
        this.threadNumber = threadNumber;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public String getToken() {
        return token;
    }

    public static class Builder {
        private String token;
        private int threadNumber;

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setThreadNumber(int threadNumber) {
            this.threadNumber = threadNumber;
            return this;
        }

        public BotConfig build() {
            return new BotConfig(token, threadNumber);
        }
    }

}
