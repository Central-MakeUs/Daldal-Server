package com.mm.api.domain.buy.dto.response;

import java.util.List;

public record BuyMeListResponse(Boolean isLastPage, List<BuyResponse> buyResponses) {
}
