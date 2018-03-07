package com.krunal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class FileConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(FileConsumer.class);

    private ExecutorService executorService;
    private String readLine;

    private static Pattern openMessageRegex = Pattern.compile("(.*?<dependency>*:{0,1}>)(.*)");
    private static Pattern closeMessageRegex = Pattern.compile("(.*?</dependency>*:{0,1}>)(.*)");
    //private static Pattern openTagRegex = Pattern.compile("(.*<[a-zA-Z][a-zA-Z-_]*:{0,1}>)(.*)");
    //private static Pattern closeTagRegex = Pattern.compile("(.*?</[a-zA-Z][a-zA-Z-_]*:{0,1}>)(.*)");

    public FileConsumer() {
        executorService = Executors.newFixedThreadPool(1);
    }

    @Inject
    private FileAdapter fileAdapter;

    public void start() {
        executorService.execute(() -> {
            try {
                while (true) {
                    parseLine(fileAdapter.readLine());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void parseLine(final String line) {
        if (line != null) {
            if (closeMessageRegex.matcher(line).matches()) {
                LOG.info(line);
            }
        }
    }
}
