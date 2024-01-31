package com.mm.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public record CommonResponse<T>(String status, T data) {
    public static <T> CommonResponse created(T data) {
        return new CommonResponse("created", data);
    }

    public static <T> CommonResponse ok(T data) {
        return new CommonResponse("ok", data);
    }

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public static <T> CommonResponse noContent() {
        return new CommonResponse("noContent", null);
    }
}
