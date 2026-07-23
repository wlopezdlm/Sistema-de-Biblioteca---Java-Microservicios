package pe.codigo.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secretBase64;

    @Value("${security.jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretBase64);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @SuppressWarnings("java:S2143")
    public String generarToken(UserDetails usuario) {
        Instant ahora = Instant.now();
        Instant expira = ahora.plusMillis(expirationMs);

        return Jwts.builder()
                .subject(usuario.getUsername())
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(expira))
                .signWith(getKey())
                .compact();
    }

    public String extraerUsername(String token) {
        return parsearClaims(token).getSubject();
    }

    public boolean esValido(String token, UserDetails usuario) {
        final String username = extraerUsername(token);
        return username.equals(usuario.getUsername()) && !estaVencido(token);
    }

    private boolean estaVencido(String token) {
        return parsearClaims(token).getExpiration().toInstant().isBefore(Instant.now());
    }

    private Claims parsearClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}