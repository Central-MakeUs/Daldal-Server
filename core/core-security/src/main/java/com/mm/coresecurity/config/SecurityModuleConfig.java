package com.mm.coresecurity.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "com.mm")
@ComponentScan(basePackages = "com.mm")
public class SecurityModuleConfig {
}
