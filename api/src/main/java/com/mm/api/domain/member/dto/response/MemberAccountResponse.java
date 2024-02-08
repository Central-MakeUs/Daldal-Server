package com.mm.api.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberAccountResponse(String depositorName, String account, String accountBank) {
}
