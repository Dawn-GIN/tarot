package com.dawn.tarot.interfaces.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dawn.tarot.domain.model.TokenPayload;
import com.dawn.tarot.domain.service.TokenService;
import com.dawn.tarot.interfaces.support.UserContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    public JwtAuthenticationFilter(TokenService tokenService) { this.tokenService = tokenService; }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = bearerToken(request);
        if (token != null) {
            try {
                TokenPayload payload = tokenService.parse(token);
                if (!"access".equals(payload.getType())) throw new IllegalArgumentException("not access token");
                UserContext.set(payload.getUserId());
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(payload.getUserId(), null, List.of()));
            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                UserContext.clear();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("{\"code\":\"UNAUTHORIZED\",\"message\":\"登录状态无效或已过期\",\"data\":null}");
                return;
            }
        }
        try {
            chain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
            UserContext.clear();
        }
    }

    private String bearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) return authorization.substring(7);
        // 原生 EventSource 不支持自定义请求头，SSE 端点使用短期访问令牌参数。
        if (request.getRequestURI().endsWith("/api/tarot/interpret")) return request.getParameter("access_token");
        return null;
    }
}
