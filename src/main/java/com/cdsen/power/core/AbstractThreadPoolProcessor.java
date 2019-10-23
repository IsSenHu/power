package com.cdsen.power.core;

import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;

/**
 * @author HuSen
 * create on 2019/9/5 14:10
 */
public abstract class AbstractThreadPoolProcessor<T> implements Processor<T> {

    private ThreadPoolTaskExecutor taskExecutor;

    public AbstractThreadPoolProcessor(int corePoolSize, Duration keepAlive, int maxPoolSize, int queueCapacity, String threadNamePrefix) {
        taskExecutor = new TaskExecutorBuilder()
                .corePoolSize(corePoolSize)
                .keepAlive(keepAlive)
                .maxPoolSize(maxPoolSize)
                .queueCapacity(queueCapacity)
                .threadNamePrefix(threadNamePrefix)
                .build();
        taskExecutor.initialize();
    }

    @Override
    public void handle(T t) {
        taskExecutor.execute(runnable(t));
    }

    /**
     * 返回一个runnable 处理逻辑
     *
     * @param t 处理的数据
     * @return Runnable
     */
    protected abstract Runnable runnable(T t);
}
