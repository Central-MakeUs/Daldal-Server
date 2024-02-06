package com.mm.coresecurity.util;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

public class HttpResponseUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void writeSuccessResponse(HttpServletResponse response, Map<String, String> data) throws IOException {
		// String json = objectMapper.writeValueAsString(data);
		// response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		// response.setStatus(HttpServletResponse.SC_OK);
		// response.setCharacterEncoding("UTF-8");
		// response.getWriter().write(json);

		String redirectUrl = "http://localhost:5173";
		StringBuffer sb = new StringBuffer(redirectUrl);
		sb.append("?").append("access-token=").append(data.get("accessToken"));
		sb.append("&").append("refresh-token=").append(data.get("refreshToken"));

		response.sendRedirect(sb.toString());
	}

	public static void writeErrorResponse(HttpServletResponse response, HttpStatus httpStatus, Object data) throws
		IOException {
		// String json = objectMapper.writeValueAsString(data);
		// response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		// response.setStatus(httpStatus.value());
		// response.setCharacterEncoding("UTF-8");
		// response.getWriter().write(json);

		String redirectUrl = "http://localhost:5173";
		StringBuffer sb = new StringBuffer(redirectUrl);
		sb.append("?").append("error-message=").append(data);

		response.sendRedirect(sb.toString());
	}
}
