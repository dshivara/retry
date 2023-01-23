package com.test.retry.backoff;

public class ExponentialBackoffStrategy implements BackoffStrategy {

    public static final double DOUBLE = 1.0d;
    private final Integer interval;

    private final Double backoff;

    public ExponentialBackoffStrategy(Integer interval, Double backoff) {
        this.interval = interval;
        this.backoff = backoff;
    }

    @Override
    public Integer getInterval(Integer times) {
        return (int) (interval * Math.pow(backoff, times * DOUBLE));
    }
}
