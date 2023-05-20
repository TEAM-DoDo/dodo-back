package kr.ac.hansung.dodobackend.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;
@Component
public class JwtTokenProvider {
    private Key key;
    private final long validityInMilliseconds = 86400000L;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());;
        System.out.println("Publish TEST Token " + createToken());
    }

    //토큰생성
    public TokenInfo createToken() {
        Claims claims = Jwts.claims().setSubject("user");
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + validityInMilliseconds);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + validityInMilliseconds * 3))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String accessToken) {
        //System.out.println(accessToken);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("Invalid JWT Token");
            //e.printStackTrace();
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT Token");
            //e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT Token");
            //e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty.");
            //e.printStackTrace();
        } catch (Exception e){

        }
        return false;
    }
    //유효한 토큰인지 확인
    public Claims parseClaim(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
