package com.test.retry.executor;

import com.test.retry.configuration.ConfigurationSingleton;
import com.test.retry.jobs.DelayedJob;
import com.test.retry.jobs.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class JobConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(JobConsumer.class);

    private final BlockingQueue<Job<Void>> jobQueue;

    private final BlockingQueue<DelayedJob> failedJobsQueue;

    public JobConsumer(BlockingQueue<Job<Void>> jobQueue, BlockingQueue<DelayedJob> failedJobsQueue) {
        this.jobQueue = jobQueue;
        this.failedJobsQueue = failedJobsQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Job<Void> task = jobQueue.take();
                execute(task);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void execute(Job<Void> task) {
        try {
            logger.info("Executing job : {}", task);
            task.execute();
        } catch (Exception e) {
            handleRetry(task);
        }
    }

    private void handleRetry(Job<Void> task) {
        logger.info("Retrying job: {}", task);
        int initialAttempt = 1;
        DelayedJob delayedJob = new DelayedJob(task, ConfigurationSingleton.INSTANCE.getJitterStrategy().jitter(), ConfigurationSingleton.INSTANCE.getBackOffStrategy().getInterval(initialAttempt), initialAttempt);
        failedJobsQueue.offer(delayedJob);
    }
}
