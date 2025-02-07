package ru.develgame.gpsserver.backend.security.jwt.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ru.develgame.gpsserver.backend.exception.JwtAuthenticationException;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtAuthService jwtAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            SecurityContextHolder.getContext().setAuthentication(jwtAuthService.getAuth(request));
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            handlerExceptionResolver.resolveException(request, response, null,
                    new JwtAuthenticationException("JWT token is expired or invalid."));
        }
        filterChain.doFilter(request, response);
    }
}
