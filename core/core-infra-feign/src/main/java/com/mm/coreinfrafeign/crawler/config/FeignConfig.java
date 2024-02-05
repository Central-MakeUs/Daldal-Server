package com.mm.coreinfrafeign.crawler.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.mm")
@EnableFeignClients(basePackages = "com.mm")
public class FeignConfig {
}
