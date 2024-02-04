package com.mm.coreinfrafeign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mm.coreinfrafeign.dto.requset.ZigZagCrawlerRequest;
import com.mm.coreinfrafeign.dto.response.ZigZagCrawlerResponse;

@FeignClient(name = "ZigZagCrawler", url = "https://87vlx0pzf0.execute-api.ap-northeast-2.amazonaws.com/crawler/zigzag")
public interface ZigZagCrawlerClient {
	@GetMapping
	ZigZagCrawlerResponse call(
		@RequestBody ZigZagCrawlerRequest request
	);
}
