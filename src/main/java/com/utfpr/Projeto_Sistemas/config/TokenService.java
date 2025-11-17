package com.utfpr.Projeto_Sistemas.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.utfpr.Projeto_Sistemas.entities.Company;
import com.utfpr.Projeto_Sistemas.utilities.TokenResponse;
import com.utfpr.Projeto_Sistemas.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    @Autowired
    private TokenWhitelist tokenWhitelist;

    public TokenResponse generateToken(UserDetails userDetails) {
      try{
          String subjectId;
          String username;
          String role;

          if (userDetails instanceof User user) {
              subjectId = String.valueOf(user.getIdUser());
              username = user.getUsername();
              role = user.getRole().getRole();
          } else if (userDetails instanceof Company company ) {
              subjectId = String.valueOf(company.getIdCompany());
              username = company.getUsername();
              role = company.getRole().getRole();
          } else {
              throw new IllegalArgumentException("Error : " + userDetails.getClass().getName());
          }
          Algorithm algorithm = Algorithm.HMAC256(secret);
          String token = JWT.create()
                  .withIssuer("auth_api")
                  .withSubject(String.valueOf(subjectId))
                  .withExpiresAt(Instant.now().plusSeconds(genExpirationDate()))
                  .withClaim("username", username)
                  .withClaim("role", role)
                  .sign(algorithm);
          tokenWhitelist.add(token, genExpirationDate());
          return new TokenResponse(token, genExpirationDate());
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
    public String getRoleFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)  //(Algorithm.HMAC256(token))
                    .withIssuer("auth_api")
                    .build()
                    .verify(token)
                    .getClaim("role").asString();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    public Instant removeToken(String token) {
        return tokenWhitelist.remove(token);
    }

    public boolean verifyToken(String token) {
        if (token == null || !token.startsWith("Bearer ")){  //verify received token
            return false;
        }
        return true;
    }

    public String replaceToken (String token) {
        return token.replace("Bearer ", ""); //cut off "Bearer "
    }

    private Integer genExpirationDate() {

        return 3600; //1h
    }
}
