package com.test.retry.backoff;

public interface BackoffStrategy {
    Integer getInterval(Integer times);
}
