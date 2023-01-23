package com.test.retry.backoff;

public class FixedIntervalBackoffStrategy implements BackoffStrategy {
    private final Integer interval;

    public FixedIntervalBackoffStrategy(Integer interval) {
        this.interval = interval;
    }

    @Override
    public Integer getInterval(Integer times) {
        return interval;
    }
}
