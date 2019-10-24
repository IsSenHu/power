package com.cdsen.power.core.security.filter;

import com.cdsen.power.core.security.model.UserDetailsImpl;
import com.cdsen.power.core.security.util.JwtUtils;
import com.cdsen.user.SecurityConfig;
import com.cdsen.user.UserLoginInfo;
import com.cdsen.user.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/8/27 18:07
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserManager userManager;
    private final SecurityConfig securityConfig;
    public JwtAuthenticationTokenFilter(JwtUtils jwtUtils, UserManager userManager, SecurityConfig securityConfig) {
        this.jwtUtils = jwtUtils;
        this.userManager = userManager;
        this.securityConfig = securityConfig;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws IOException, ServletException {
        String token = httpServletRequest.getHeader(securityConfig.getHeader());
        String username;
        UserLoginInfo loginInfo = null;
        boolean set = StringUtils.isNotBlank(token)
                && StringUtils.isNotBlank((username = jwtUtils.getUsernameFromToken(token.trim())))
                && SecurityContextHolder.getContext().getAuthentication() == null
                && Objects.nonNull(loginInfo = userManager.getLoginInfo(token))
                && jwtUtils.validateToken(token, username);

        if (set) {
            UserDetailsImpl userDetails = new UserDetailsImpl(
                    loginInfo.getUserId(),
                    loginInfo.getUsername(),
                    loginInfo.getPassword(),
                    loginInfo.isAccountNonLocked(),
                    loginInfo.isEnabled(),
                    loginInfo.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
            );
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
