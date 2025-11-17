package com.utfpr.Projeto_Sistemas.config;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenWhitelist {
    private static final Map<String, Instant> whitelist = new ConcurrentHashMap<>();

    public void add(String token, Integer expiration) {
        Instant expirationDate = Instant.now().plusSeconds(expiration);
        whitelist.put(token, expirationDate);
    }

    public boolean exists(String token) {
        Instant exp = whitelist.get(token);
        if (exp == null) return false;
        if (Instant.now().isAfter(exp)) {
            whitelist.remove(token);
            return false;
        }
        return true;
    }

    public Instant remove(String token) {
        return whitelist.remove(token);
    }
}
