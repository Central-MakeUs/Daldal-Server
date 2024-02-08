package com.mm.api.domain.admin.dto.response;

import java.util.List;

import com.mm.api.domain.buy.dto.response.BuyResponse;

public record BuyListResponse(Long pageNum, List<BuyResponse> buys) {
}
