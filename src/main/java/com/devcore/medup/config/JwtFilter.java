package com.devcore.medup.config;

import com.devcore.medup.services.CustomUserDetailsService;
import com.devcore.medup.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7);
            String email = jwtService.pegarEmail(token);

            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Token sem usuário");
            }

            UserDetails user = userDetailsService.loadUserByUsername(email);

            if (!user.isEnabled()) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Conta não ativada"
                );
                return;
            }

            var authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (
                io.jsonwebtoken.JwtException |
                IllegalArgumentException |
                org.springframework.security.core.userdetails.UsernameNotFoundException e
        ) {
            SecurityContextHolder.clearContext();

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token inválido ou expirado"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}
