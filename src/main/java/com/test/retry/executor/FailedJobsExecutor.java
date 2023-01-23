package com.test.retry.executor;

import com.test.retry.configuration.ConfigurationSingleton;
import com.test.retry.jobs.DelayedJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class FailedJobsExecutor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(JobConsumer.class);

    private final BlockingQueue<DelayedJob> failedJobsQueue;

    public FailedJobsExecutor(BlockingQueue<DelayedJob> failedJobsQueue) {
        this.failedJobsQueue = failedJobsQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                DelayedJob delayedJob = failedJobsQueue.take();
                execute(delayedJob);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void execute(DelayedJob delayedJob) {
        try {
            logger.info("Executing delayed job : {}", delayedJob);
            delayedJob.execute();
        } catch (Exception e) {
            if (delayedJob.getNextRetryAttempt() < ConfigurationSingleton.INSTANCE.getMaxAttempts()) {
                logger.info("Rescheduling delayed job : {}", delayedJob);
                failedJobsQueue.offer(delayedJob.retry(ConfigurationSingleton.INSTANCE.getJitterStrategy().jitter(), ConfigurationSingleton.INSTANCE.getBackOffStrategy().getInterval(delayedJob.getNextRetryAttempt())));
            } else {
                logger.info("Max attempts exceeded for delayed job : {}", delayedJob);
            }
        }
    }
}
