package com.xiaohai.admin.confing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池的配置类
 * @author wangchenghai
 * @date 2023/02/03 14:00:06
 */
@Configuration
/**
 * 启用异步任务
 */
@EnableAsync
public class TaskPoolConfig {
    @Value("${task.executor.core_pool_size}")
    private int corePoolSize;
    @Value("${task.executor.max_pool_size}")
    private int maxPoolSize;
    @Value("${task.executor.queue_capacity}")
    private int queueCapacity;
    @Value("${task.executor.keep_alive_seconds}")
    private int keepAliveSeconds;
    @Value("${task.executor.name_prefix}")
    private String namePrefix;

    @Bean(name = "syncExecutorPool")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        taskExecutor.setCorePoolSize(corePoolSize);
        ///配置最大线程数
        taskExecutor.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        taskExecutor.setQueueCapacity(queueCapacity);
        /**
         * 线程池维护线程所允许的空闲时间--单位秒，超过销毁
         * 线程池线程数量大于corePoolSize时候，多出来的空闲线程，多长时间会被销毁
         */
        //线程存活时间
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        //配置线程池中的线程的名称前缀
        taskExecutor.setThreadNamePrefix(namePrefix);
        // 该方法用来设置 线程池关闭 的时候 等待 所有任务都完成后，再继续 销毁 其他的 Bean，
        // 这样这些 异步任务 的 销毁 就会先于 数据库连接池对象 的销毁。
        // 线程活跃时间（秒）表明等待所有线程执行完，默认为false。
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //等待所有任务结束后再关闭线程池（任务的等待时间 如果超过这个时间还没有销毁就 强制销毁，以确保应用最后能够被关闭，而不是阻塞住）
        taskExecutor.setAwaitTerminationSeconds(60);
        //设置拒绝策略 （线程不够用时由调用的线程处理该任务）
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        taskExecutor.initialize();

        return taskExecutor;

    }
}
