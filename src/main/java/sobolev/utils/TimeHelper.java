package sobolev.utils;

import static java.lang.Thread.sleep;

/**
 * Created by kirrok on 18.01.17.
 */

public class TimeHelper {
    public static void goSleep(long millis) {
        try {
            sleep(millis);
        } catch (RuntimeException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
