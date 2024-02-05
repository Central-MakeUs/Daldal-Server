package com.mm.api.domain.buy.dto.response;

import java.util.List;

public record BuyListResponse(Long pageNum, List<BuyResponse> buys) {
}
