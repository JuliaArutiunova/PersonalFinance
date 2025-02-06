package by.it_academy.jd2.user_service.controller.utils;

import by.it_academy.jd2.user_service.config.property.JWTProperty;
import by.it_academy.lib.dto.TokenInfoDto;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenHandler {

    private final JWTProperty jwtProperty;


    public JwtTokenHandler(JWTProperty jwtProperty) {
        this.jwtProperty = jwtProperty;

    }

    public String generateToken(TokenInfoDto tokenInfoDTO) {


        return Jwts.builder()
                .setSubject(tokenInfoDTO.getId())
                .claim("role", tokenInfoDTO.getRole())
                .setIssuer(jwtProperty.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .signWith(SignatureAlgorithm.HS512, jwtProperty.getSecret())
                .compact();
    }



    public TokenInfoDto getTokenInfo(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return new TokenInfoDto(claims.getSubject(), (String) claims.get("role"));
    }

    public boolean validate(String token) {

        try {
            Jwts.parser().setSigningKey(jwtProperty.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException | IllegalArgumentException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException ex) {
            return false;
        }

    }


}
