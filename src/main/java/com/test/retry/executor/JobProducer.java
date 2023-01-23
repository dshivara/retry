package com.test.retry.executor;

import com.test.retry.jobs.AlwaysFailingJob;
import com.test.retry.jobs.AlwaysSucceedingJob;
import com.test.retry.jobs.IntermittentFailingJob;
import com.test.retry.jobs.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class JobProducer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(JobProducer.class);

    private int numberOfAlwaysSucceedingJobs = 20;
    private int numberOfIntermittentFailingJobs = 10;
    private int numberOfAlwaysFailingJobs = 10;

    private final BlockingQueue<Job<Void>> jobQueue;

    public JobProducer(BlockingQueue<Job<Void>> jobQueue) {
        this.jobQueue = jobQueue;
    }

    @Override
    public void run() {
        logger.info("Producing intermittent failing jobs");
        int j = 0;
        for (int i = 0; i < numberOfIntermittentFailingJobs; i++) {
            jobQueue.offer(new IntermittentFailingJob(String.valueOf(j++)));
        }
        logger.info("Producing always failing jobs");
        for (int i = 0; i < numberOfAlwaysFailingJobs; i++) {
            jobQueue.offer(new AlwaysFailingJob(String.valueOf(j++)));
        }
        logger.info("Producing always succeeding jobs");
        for (int i = 0; i < numberOfAlwaysSucceedingJobs; i++) {
            jobQueue.offer(new AlwaysSucceedingJob(String.valueOf(j++)));
        }
    }
}