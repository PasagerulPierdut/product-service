package com.accenture.productservice.utils.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

@Configuration
public class LoggingUtility {

    @Bean
    public static void initLogManager() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/main/resources/logging.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
