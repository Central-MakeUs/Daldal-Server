package com.mm.api.domain.admin.dto.response;

import java.util.List;

public record WithdrawListResponse(Long pageNum, List<WithdrawResponse> withdrawResponses) {
}
