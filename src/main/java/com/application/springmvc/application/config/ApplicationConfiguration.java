package com.application.springmvc.application.config;

import com.mimu.common.log.springmvc.config.LogTraceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Import(LogTraceConfig.class)
public class ApplicationConfiguration {

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(2);
    }
}
