package com.test.retry.jobs;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record AlwaysSucceedingJob(String id) implements Job<Void> {
    private static final Logger logger = LoggerFactory.getLogger(AlwaysSucceedingJob.class);

    @Override
    public Void execute() throws Exception {
        Thread.sleep(100);
        logger.info("Executing always succeeding job with id: {}", id);
        return null;
    }

    @Override
    public String toString() {
        return String.format("always-succeeding:%s", id());
    }
}
