package com.utfpr.Projeto_Sistemas.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.utfpr.Projeto_Sistemas.entities.TokenResponse;
import com.utfpr.Projeto_Sistemas.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    @Autowired
    private TokenWhitelist tokenWhitelist;

    public TokenResponse generateToken(User user) {
      try{
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer("auth_api")
                .withSubject(String.valueOf(user.getIdUser()))
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);
        tokenWhitelist.add(token, genExpirationDate());
        return new TokenResponse(token, String.valueOf(genExpirationDate()));
      } catch (JWTVerificationException exception){
            throw new RuntimeException("Error while generating JWT Token", exception);
      }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)  //(Algorithm.HMAC256(token))
                    .withIssuer("auth_api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    public Instant removeToken(String token) {
        return tokenWhitelist.remove(token);
    }

    public boolean verifyToken(String token) {
        if (token == null || !token.startsWith("Bearer ")){  //verify received token
            return true;
        }
        return false;
    }

    public String replaceToken (String token) {
        return token.replace("Bearer ", ""); //cut off "Bearer "
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
