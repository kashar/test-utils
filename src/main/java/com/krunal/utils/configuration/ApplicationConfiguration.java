package com.krunal.utils.configuration;

import com.krunal.utils.FileAdapter;
import com.krunal.utils.FileConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Value("${file.path}")
    private String filePath;

    @Value("${user}")
    private String user;

    @Value("${password}")
    private String password;

    @Value("${host}")
    private String host;

    @Value("${file.name}")
    private String fileName;

    @Bean
    FileConsumer fileConsumer() {
        return new FileConsumer();
    }

    @Bean
    FileAdapter fileAdapter() {
        return new FileAdapter(filePath + "/" + fileName);
    }
}
