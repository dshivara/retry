package com.test.retry.configuration;

import com.test.retry.backoff.BackoffStrategy;
import com.test.retry.backoff.ExponentialBackoffStrategy;
import com.test.retry.jitter.JitterStrategy;
import com.test.retry.jitter.RandomJitterStrategy;

public enum ConfigurationSingleton {

    INSTANCE() {
        @Override
        public BackoffStrategy getBackOffStrategy() {
            return new ExponentialBackoffStrategy(1000, 1.8);
        }

        @Override
        public JitterStrategy getJitterStrategy() {
            return new RandomJitterStrategy(500);
        }

        @Override
        public Integer getMaxAttempts() {
            return 5;
        }
    };

    public abstract BackoffStrategy getBackOffStrategy();

    public abstract JitterStrategy getJitterStrategy();

    public abstract Integer getMaxAttempts();

}
