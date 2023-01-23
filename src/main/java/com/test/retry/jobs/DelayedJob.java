package com.test.retry.jobs;

import java.util.Comparator;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedJob implements Job<Void>, Delayed {
    private final Job<Void> original;
    private final Integer jitter;
    private final Integer delayDuration;
    private final Integer retryAttempt;
    private final Long expiration;

    public DelayedJob(Job<Void> original, Integer jitter, Integer delayDuration, Integer retryAttempt) {
        this.original = original;
        this.jitter = jitter;
        this.delayDuration = delayDuration;
        this.expiration = System.currentTimeMillis() + delayDuration;
        this.retryAttempt = retryAttempt;
    }

    public DelayedJob retry(Integer jitter, Integer delayDuration) {
        return new DelayedJob(original, jitter, delayDuration, getNextRetryAttempt());
    }

    @Override
    public String id() {
        return original.id();
    }

    @Override
    public Void execute() throws Exception {
        Thread.sleep(jitter);
        return original.execute();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long remainingDelayMillis = delayDuration - System.currentTimeMillis();
        return unit.convert(remainingDelayMillis, TimeUnit.MILLISECONDS);
    }

    public Integer getRetryAttempt() {
        return retryAttempt;
    }

    public Integer getNextRetryAttempt() {
        return retryAttempt + 1;
    }

    public Long getExpiration() {
        return expiration;
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedJob other = (DelayedJob) o;
        return Comparator.comparing(DelayedJob::getExpiration).thenComparing(DelayedJob::id).compare(this, other);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DelayedJob{");
        sb.append("original=").append(original);
        sb.append(", jitter=").append(jitter);
        sb.append(", delayDuration=").append(delayDuration);
        sb.append(", retryAttempt=").append(retryAttempt);
        sb.append('}');
        return sb.toString();
    }
}
