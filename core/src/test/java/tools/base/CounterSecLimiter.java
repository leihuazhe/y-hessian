package tools.base;

/**
 * 指定时间内计数限流器
 */
public class CounterSecLimiter {

    private int couter;

    private long lastResetTime;

    private int minute;

    public CounterSecLimiter(int minute) {
        couter = 0;
        minute = minute > 0 ? minute : 1;
        lastResetTime = System.currentTimeMillis() + 1000 * 60 * minute;//
    }

    public int addAndGet() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > lastResetTime) {
            couter = 0;
            lastResetTime = currentTime + 1000 * 60 * minute;
        }
        couter++;
        return couter;
    }
}
