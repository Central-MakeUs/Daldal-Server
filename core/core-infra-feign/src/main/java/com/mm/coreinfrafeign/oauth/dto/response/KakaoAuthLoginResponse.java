package com.mm.coreinfrafeign.oauth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoAuthLoginResponse(@JsonProperty("access_token") String accessToken) {
}
