package tools.base;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Denim.leihz 2019-08-12 3:22 PM
 */
public class LocalRateLimiter {

    //每秒只发出5个令牌
    RateLimiter rateLimiter = RateLimiter.create(0.002);

    /**
     * 尝试获取令牌
     *
     * @return
     */
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire(1);
    }

    public static void main(String[] args) {
        final LocalRateLimiter limiter = new LocalRateLimiter();
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (limiter.tryAcquire()) {
                            System.out.println(Thread.currentThread().getName() + " acquire token, ts: " + System.currentTimeMillis() / 1000);
                        }
                    }
                }
            });
        }
    }
}
