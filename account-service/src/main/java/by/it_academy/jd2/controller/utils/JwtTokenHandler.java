package by.it_academy.jd2.controller.utils;


import by.it_academy.jd2.config.property.JWTProperty;
import by.it_academy.lib.dto.TokenInfoDto;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenHandler {

    private final JWTProperty jwtProperty;


    public JwtTokenHandler(JWTProperty jwtProperty) {
        this.jwtProperty = jwtProperty;

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
