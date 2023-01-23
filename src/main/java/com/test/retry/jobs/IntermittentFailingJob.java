package com.test.retry.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public record IntermittentFailingJob(String id) implements Job<Void> {
    private static final Logger logger = LoggerFactory.getLogger(IntermittentFailingJob.class);
    public static final int MILLIS = 100;

    @Override
    public Void execute() throws Exception {
        Thread.sleep(MILLIS);
        logger.info("Executing Intermittent failing job with id: {}", id);
        if (ThreadLocalRandom.current().nextBoolean()) {
            throw new JobExecutionException(String.format("Intermittent failing job with id: %d failed", id));
        } else {
            logger.info("Intermittent job with id: {} succeeded", id);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("intermittent-failing:%s", id());
    }
}
