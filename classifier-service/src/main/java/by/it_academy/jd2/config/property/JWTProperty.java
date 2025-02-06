package by.it_academy.jd2.config.property;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "app.jwt")
public class JWTProperty {

    private String secret;
    private String issuer;

}
