package com.test.retry.jobs;

public class JobExecutionException extends Exception {
    public JobExecutionException(String message) {
        super(message);
    }
}
