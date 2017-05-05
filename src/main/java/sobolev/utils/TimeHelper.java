package sobolev.utils;

import static java.lang.Thread.sleep;

public class TimeHelper {
    public static void goSleep(long millis) {
        try {
            sleep(millis);
        } catch (RuntimeException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
