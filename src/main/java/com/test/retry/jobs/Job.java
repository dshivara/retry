package com.test.retry.jobs;

public interface Job<T> {
    public String id();
    public T execute() throws Exception;
}
