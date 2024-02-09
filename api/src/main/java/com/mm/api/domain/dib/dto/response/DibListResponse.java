package com.mm.api.domain.dib.dto.response;

import java.util.List;

import com.mm.api.domain.item.dto.response.ItemResponse;

public record DibListResponse(Boolean isLastPage, Long count, List<ItemResponse> itemResponses) {
}
