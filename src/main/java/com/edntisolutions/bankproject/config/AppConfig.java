package com.edntisolutions.bankproject.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients({"com.edntisolutions.bankproject.client"})
public class AppConfig {
}
