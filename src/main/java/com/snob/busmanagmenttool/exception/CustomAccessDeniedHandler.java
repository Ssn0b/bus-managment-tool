package com.snob.busmanagmenttool.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
    response.sendError(
        HttpServletResponse.SC_FORBIDDEN, "Access denied.\nYou don't have permission for this");
    }
}