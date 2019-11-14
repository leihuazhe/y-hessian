//package tools.base;
//
//import org.apache.commons.lang3.math.NumberUtils;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
//
//import java.util.Properties;
//
///**
// * @author Denim.leihz 2019-08-12 3:38 PM
// */
//public class GuavaLocalCounter implements LocalLimiter {
//
//    private Logger logger = LoggerFactory.getLogger(GuavaLocalCounter.class);
//
//    public static ThreadLocal<CounterSecLimiter> TRACER_LIMITER = new ThreadLocal<>();
//
//    /**
//     * 令牌数量
//     */
//    private static int maxCounter = 0;
//
//    private static int minute = 15;
//
//    public boolean limiting() {
//        String threadName = Thread.currentThread().getName();
//        CounterSecLimiter rateLimiter = TRACER_LIMITER.get();
//        if (rateLimiter == null) {
//            /* 如果对应的令牌桶不存在则创建 */
//            logger.debug("create RateLimiter for [{}]", threadName);
//            if (maxCounter > 0) {
//                rateLimiter = new CounterSecLimiter(minute);
//                TRACER_LIMITER.set(rateLimiter);
//            } else {
//                return true;
//            }
//        }
//        if (rateLimiter == null) {
//            return true;
//        }
//        if (rateLimiter.addAndGet() <= maxCounter) {
//            logger.trace("serviceName-> [{}] 成功获取到token,允许采样!", threadName);
//            return true;
//        } else {
//            logger.trace("serviceName-> [{}] 获取到token失败!", threadName);
//        }
//        return false;
//    }
//
//    public void notify(Properties properties) {
//        int maxCounter = NumberUtils.toInt(properties.getProperty("localPermitsPerSecond"), 0);
//        if (maxCounter > 0 && maxCounter < 20) {
//            this.maxCounter = 20;
//        } else {
//            this.maxCounter = maxCounter;
//        }
//    }
//
//    public void start(Properties properties, boolean restart) {
//        notify(properties);
//        stop();
//    }
//
//    public void stop() {
//        //等待线程自己线束
//    }
//
//    public static void main(String[] args) {
//
//    }
//}