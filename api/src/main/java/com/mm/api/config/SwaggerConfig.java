package com.mm.api.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
	info = @Info(title = "달달 쇼핑 백엔드 API 명세서",
		description = "달달 쇼핑 백엔드 API 명세서 입니다",
		version = "0.1"),
	servers = {
		@Server(url = "/", description = "달달 서버 url")}
)
@Configuration
public class SwaggerConfig {
}
