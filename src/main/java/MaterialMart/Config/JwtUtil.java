package MaterialMart.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    // Generate signing key
    private Key key() {
        System.out.println("🟦 JwtUtil: Loading signing key...");
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Key getKey() {
        return key();
    }

    // Generate Token
    public String generateToken(String username, String role) {
        System.out.println("🟦 JwtUtil: Generating token for user " + username);

        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key())
                .compact();

        System.out.println("🟩 JwtUtil: Token generated = " + token);
        return token;
    }

    // Extract username
    public String getUsername(String token) {
        System.out.println("🟦 JwtUtil: Extracting username from token...");
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract all claims
    public Claims getClaims(String token) {
        System.out.println("🟦 JwtUtil: Extracting claims...");
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate token (WITH LOGS)
    public boolean validateToken(String token) {
        System.out.println("🟦 JwtUtil: Validating token: " + token);

        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            System.out.println("🟩 JwtUtil: Token VALID");
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("🟥 EXPIRED TOKEN: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.out.println("🟥 UNSUPPORTED TOKEN: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("🟥 MALFORMED TOKEN: " + ex.getMessage());
        } catch (SignatureException ex) {
            System.out.println("🟥 INVALID SIGNATURE (SECRET MISMATCH): " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("🟥 EMPTY TOKEN: " + ex.getMessage());
        }
        return false;
    }
}
