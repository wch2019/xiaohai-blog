//package com.xiaohai.admin.confing.aspectj;
//
//import com.google.common.util.concurrent.RateLimiter;
//import com.xiaohai.common.annotation.RateLimit;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Aspect
//@Component
//public class RateLimitAspect {
//    private final ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();
//    private final ConcurrentHashMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();
//    // 1分钟窗口
//    private final long TIME_WINDOW_MS = 60000;
//
//    @Around("@annotation(rateLimit)")
//    public Object limit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
//        String methodName = joinPoint.getSignature().toShortString();
//
//        limiters.putIfAbsent(methodName, RateLimiter.create(rateLimit.value()));
//        counters.putIfAbsent(methodName, new AtomicInteger(0));
//
//        RateLimiter rateLimiter = limiters.get(methodName);
//        AtomicInteger counter = counters.get(methodName);
//
//        // 清理过期计数
//        if (counter.get() == 0) {
//            resetCounter(methodName);
//        }
//
//        if (rateLimiter.tryAcquire() && counter.incrementAndGet() <= rateLimit.limit()) {
//            return joinPoint.proceed();
//        } else {
//            throw new RuntimeException("Too many requests");
//        }
//    }
//
//    private void resetCounter(String methodName) {
//        // 1分钟后重置计数器
//        new Thread(() -> {
//            try {
//                Thread.sleep(TIME_WINDOW_MS);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//            counters.remove(methodName);
//        }).start();
//    }
//}
