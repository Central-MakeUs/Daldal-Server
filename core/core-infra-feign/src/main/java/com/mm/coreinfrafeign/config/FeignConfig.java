package com.mm.coreinfrafeign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.mm.coreinfrafeign")
public class FeignConfig {
}
