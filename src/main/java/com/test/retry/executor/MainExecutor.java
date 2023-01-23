package com.test.retry.executor;

import com.test.retry.jobs.DelayedJob;
import com.test.retry.jobs.Job;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class MainExecutor {
    public static final int CAPACITY = 50;
    public static final int TIMEOUT = 5;
    private final ExecutorService jobExecutor;

    public MainExecutor() {
        this.jobExecutor = Executors.newFixedThreadPool(5);
    }

    public void start() throws InterruptedException {
        BlockingQueue<Job<Void>> jobQueue = new ArrayBlockingQueue<>(CAPACITY);
        BlockingQueue<DelayedJob> delayedJobBlockingQueue = new DelayQueue<>();

        JobProducer jobProducer = new JobProducer(jobQueue);
        JobConsumer jobConsumer = new JobConsumer(jobQueue, delayedJobBlockingQueue);
        FailedJobsExecutor failedJobsExecutor = new FailedJobsExecutor(delayedJobBlockingQueue);

        jobExecutor.execute(jobConsumer);
        jobExecutor.execute(jobProducer);
        jobExecutor.execute(failedJobsExecutor);
        jobExecutor.awaitTermination(TIMEOUT, TimeUnit.MINUTES);
    }
}
