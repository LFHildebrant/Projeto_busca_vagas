package com.utfpr.Projeto_Sistemas.config;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenWhitelist {
    private static final Map<String, Instant> whitelist = new ConcurrentHashMap<>();

    public void add(String token, Instant expiration) {
        whitelist.put(token, expiration);
    }

    public boolean exists(String token) {
        Instant exp = whitelist.get(token);
        if (exp == null) return false;
        if (exp.isBefore(Instant.now())) {
            whitelist.remove(token);
            return false;
        }
        return true;
    }

    public Instant remove(String token) {
        return whitelist.remove(token);
    }
}
