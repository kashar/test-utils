package com.krunal.utils.configuration;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class SshSessionConfiguration {

    private Session sshSession;
    private JSch jsch;

    @Value("${file.path}")
    private String logFilePath;

    private String user = "krunalashar";

    @Value("${password}")
    private String password;

    @Value("${host}")
    private String host;

    @Value("${file.name}")
    private String fileName;

    @Bean
    Session sshSession() throws JSchException {
        jsch = new JSch();

        Properties jschCfg = new Properties();
        jschCfg.put("PreferredAuthentications", "password");
        jschCfg.put("StrictHostKeyChecking", "no");

        sshSession = jsch.getSession(user, host);
        sshSession.setPassword(password);
        sshSession.setConfig(jschCfg);
        sshSession.connect();

        return sshSession;
    }
}
