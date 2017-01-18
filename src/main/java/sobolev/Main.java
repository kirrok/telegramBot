package sobolev;

import java.io.IOException;


/**
 * Created by kirrok on 17.01.17.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        new TestLongPoolingBot(new DefaultBotConfig()).start();
    }
}
