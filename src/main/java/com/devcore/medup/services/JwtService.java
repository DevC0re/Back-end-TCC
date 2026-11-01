package com.devcore.medup.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    public String gerarToken(String email, UUID id){
        return Jwts.builder()
                .setSubject(email)
                .claim("id", id.toString())
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();
    }

    public String pegarEmail(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
