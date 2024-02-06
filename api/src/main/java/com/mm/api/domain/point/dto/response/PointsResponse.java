package com.mm.api.domain.point.dto.response;

import java.util.List;

import com.mm.api.domain.buy.dto.response.BuyResponse;

public record PointsResponse(Integer totalPoint, List<BuyResponse> buyResponses) {
}
