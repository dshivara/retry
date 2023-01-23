package com.test.retry.jitter;

import java.util.concurrent.ThreadLocalRandom;

public record RandomJitterStrategy(Integer jitter) implements JitterStrategy {

    @Override
    public Integer jitter() {
        return (int) (jitter * (ThreadLocalRandom.current().nextFloat()));
    }
}
