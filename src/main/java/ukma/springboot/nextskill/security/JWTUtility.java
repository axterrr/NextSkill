package ukma.springboot.nextskill.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import com.auth0.jwt.JWT;
import org.springframework.stereotype.Component;

@Component
public class JWTUtility {

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Value("${TOKEN_EXPIRATION}")
    private long expirationTime;

    public String getToken(String username, Collection<? extends GrantedAuthority> authorities) {
         return JWT.create()
                .withSubject(username)
                .withClaim("roles", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public DecodedJWT verifyToken(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(token);
    }
}
