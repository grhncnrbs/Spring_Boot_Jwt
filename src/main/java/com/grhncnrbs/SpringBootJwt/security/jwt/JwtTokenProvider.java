package com.grhncnrbs.SpringBootJwt.security.jwt;

import com.grhncnrbs.SpringBootJwt.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("{SpringBootJwt.app.jwtSecret}")
    private String APP_SECRET;

    @Value("{SpringBootJwt.app.jwtExpirationMs}")
    private String EXPIRES_IN;

    public String generateJwtToken(Authentication authentication ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime()+EXPIRES_IN);

        return Jwts.builder()
                .setSubject(userDetails.getId().toString())
                .setIssuer("SpringBootJwt")
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET)
                .compact();
    }
    Long getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

    Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (SignatureException e) {
           return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
           return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
