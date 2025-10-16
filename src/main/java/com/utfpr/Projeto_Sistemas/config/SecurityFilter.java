package com.utfpr.Projeto_Sistemas.config;

import com.utfpr.Projeto_Sistemas.repository.UserRepositoy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    private final TokenService tokenService;
    private final UserRepositoy  userRepositoy;
    private final TokenWhitelist tokenWhitelist;

    public SecurityFilter(TokenService tokenService, UserRepositoy userRepositoy, TokenWhitelist tokenWhitelist) {
        this.tokenService = tokenService;
        this.userRepositoy = userRepositoy;
        this.tokenWhitelist = tokenWhitelist;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoveryToken(request);
        System.out.println("Token recebido: " + token);

        if (token != null) {
            if (tokenWhitelist.exists(token)) {
                Long idUser = Long.parseLong(tokenService.validateToken(token));

                UserDetails user = userRepositoy.findByIdUser(idUser);

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (header == null ) {
            return null;
        }
        return header.replace("Bearer ", "");
    }
}
