package com.mimu.application.application;

import com.mimu.common.log.springmvc.config.LogTraceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(LogTraceConfig.class)
public class ApplicationConfiguration {
}
