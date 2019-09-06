package com.cdsen.power.core.security.filter;

import com.cdsen.power.core.AppProperties;
import com.cdsen.power.core.security.session.SessionManage;
import com.cdsen.power.core.security.util.JwtUtils;
import com.cdsen.power.core.security.model.Session;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author HuSen
 * create on 2019/8/27 18:07
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final AppProperties appProperties;
    private final JwtUtils jwtUtils;
    private final SessionManage sessionManage;

    public JwtAuthenticationTokenFilter(AppProperties appProperties, JwtUtils jwtUtils, @Qualifier("redisSessionManage") SessionManage sessionManage) {
        this.appProperties = appProperties;
        this.jwtUtils = jwtUtils;
        this.sessionManage = sessionManage;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws IOException, ServletException {
        String token = httpServletRequest.getHeader(appProperties.getHeader());
        String username;
        Session session = null;
        boolean set = StringUtils.isNotBlank(token)
                && StringUtils.isNotBlank((username = jwtUtils.getUsernameFromToken(token.trim())))
                && SecurityContextHolder.getContext().getAuthentication() == null
                && Objects.nonNull((session = sessionManage.getSession(username)))
                && jwtUtils.validateToken(token, session);

        if (set) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(session, null, session.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
