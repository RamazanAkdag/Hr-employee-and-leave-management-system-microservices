package com.id3.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final static String SECRET_KEY =
            "e314021df8941daa688831543209e6e17e367b354045f91a97b58261edb258daf7a37ec20704c4611ccaa1813eb66baa4a6911c3b9b4aa0ebc6361a9378019845daeb3368febef8ffccd7e6ece9daed34694651f3dafd5614d2fa624a1466ef6ba2ffd1c0f805f25f7e7897d2b7f5a7ac1e73c4174a0130420676aa294cc102f8095e8fb4a4cee72c8bdb2819e4c9a007588083b296ba39fea27053638285d0fad9c883a90248464c0f259f020ed8889beb46150c10bd02fac2198adb85c40322f0d54ba889e18e564eb03ccc29fb433520aefdfba7f4f25eed80261697eeff1eeb5548855e313017bb046120a4674ed3a3308ada7507e59bcc0297356058c7d";

    String extractUserEmail(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User has no roles"))
                .getAuthority();
        claims.put("role", "ROLE_" + role);
        return generateToken(claims, userDetails);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUserEmail(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    //Tokenin içine koyacağımız kullanıcı bilgileri ve extra bilgileri parametre aldık
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token,Claims::getExpiration);
        return expiration.before(new Date());

    }

}