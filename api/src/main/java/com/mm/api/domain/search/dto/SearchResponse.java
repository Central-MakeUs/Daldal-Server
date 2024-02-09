package com.mm.api.domain.search.dto;

import java.util.List;

import com.mm.api.domain.item.dto.response.ItemResponse;

public record SearchResponse(Boolean isLastPage,
							 Long count,
							 List<ItemResponse> itemResponses) {
}
