package com.krunal.utils;

import java.io.IOException;

public interface Adapter {

    void start() throws Exception;

    void reconnect() throws Exception;

    void stop() throws IOException;

    String readLine() throws Exception;
}
