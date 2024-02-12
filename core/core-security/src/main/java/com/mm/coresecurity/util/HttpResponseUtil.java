package com.mm.coresecurity.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Map;

public class HttpResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String REDIRECT_URL = "http://localhost:5173";

    public static void writeSignUpSuccessResponse(HttpServletResponse response, Map<String, String> data) throws IOException {
        String redirectUrl = REDIRECT_URL + "/welcome";
        StringBuffer sb = new StringBuffer(redirectUrl);
        sb.append("?").append("access-token=").append(data.get("accessToken"));
        sb.append("&").append("refresh-token=").append(data.get("refreshToken"));

        response.sendRedirect(sb.toString());
    }

    public static void writeLoginSuccessResponse(HttpServletResponse response, Map<String, String> data) throws IOException {
        StringBuffer sb = new StringBuffer(REDIRECT_URL);
        sb.append("?").append("access-token=").append(data.get("accessToken"));
        sb.append("&").append("refresh-token=").append(data.get("refreshToken"));

        response.sendRedirect(sb.toString());
    }

    public static void writeErrorResponse(HttpServletResponse response, HttpStatus httpStatus, Object data) throws
            IOException {
        String redirectUrl = "http://localhost:5173";
        StringBuffer sb = new StringBuffer(redirectUrl);
        sb.append("?").append("error-message=").append(data);

        response.sendRedirect(sb.toString());
    }
}
