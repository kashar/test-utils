package com.krunal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationRunner.class);

    @Inject
    private FileAdapter fileAdapter;

    @Inject
    private FileConsumer fileConsumer;

    @Value("${file.path}")
    private String logFilePath;

    @Value("${user}")
    private String user;

    @Value("${host}")
    private String host;

    public void run() throws Exception {
        fileConsumer.start();
        fileAdapter.start();
        LOG.info("Application connected to {}, as user {}, reading from {}", host, user, logFilePath);

    }
}
