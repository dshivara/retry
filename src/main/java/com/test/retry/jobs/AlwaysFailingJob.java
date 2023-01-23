package com.test.retry.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record AlwaysFailingJob(String id) implements Job<Void> {
    private static final Logger logger = LoggerFactory.getLogger(AlwaysFailingJob.class);

    @Override
    public Void execute() throws Exception {
        Thread.sleep(100);
        throw new JobExecutionException(String.format("Always failing job with id: %d failed", id));
    }

    @Override
    public String toString() {
        return String.format("always-failing:%s", id());
    }
}
