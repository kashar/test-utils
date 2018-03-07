package com.krunal.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileAdapter implements Adapter {

    private static final Logger LOG = LoggerFactory.getLogger(FileAdapter.class);

    @Inject
    private Session session;

    private String fileName;

    private ChannelExec tailChannel;
    private BufferedReader logStream;

    public long lines = 0;

    public FileAdapter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void start() throws Exception {
        tailChannel = (ChannelExec) session.openChannel("exec");
        tailChannel.setCommand("tail -f " + fileName);
        tailChannel.connect();
        logStream = new BufferedReader(new InputStreamReader(tailChannel.getInputStream()));
    }

    @Override
    public void reconnect() throws Exception {
        if (tailChannel == null || !tailChannel.isConnected()) {
            start();
        }
    }

    @Override
    public void stop() throws IOException {
        tailChannel.disconnect();
        logStream.close();
        session.disconnect();
    }

    @Override
    public String readLine() throws Exception {
        try {
            if (logStream != null && logStream.ready()) {
                lines++;
                return logStream.readLine();
            } else {
                reconnect();
            }
        } catch (IOException e) {
            LOG.error("Error encountered trying to read line", e);
        }

        return null;
    }
}
