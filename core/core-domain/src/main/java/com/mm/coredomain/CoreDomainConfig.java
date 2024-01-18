package com.mm.coredomain;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("com.pgms.coredomain")
@EnableJpaRepositories("com.mm.coredomain")
@EnableJpaAuditing
public class CoreDomainConfig {
}

