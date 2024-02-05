package com.mm.api.domain.item.dto.response;

import java.util.List;

public record ItemListResponse(Boolean isLastPage,
							   List<ItemResponse> itemResponses) {
}
