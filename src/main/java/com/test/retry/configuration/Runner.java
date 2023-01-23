package com.test.retry.configuration;

import com.test.retry.executor.MainExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    @Autowired
    private MainExecutor mainExecutor;

    @Override
    public void run(String... args) throws Exception {
        mainExecutor.start();
    }
}
