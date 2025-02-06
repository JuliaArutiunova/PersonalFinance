package by.it_academy.jd2.user_service.service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;

@Component
public class Generator {

    private static final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    @Value("${verification.url}")
    private String url;


    public String generateVerificationMessageText(String userName, String mail, String code) {

        String link = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("code", code)
                .queryParam("mail", mail)
                .build().toUriString();

        return String.format("""
                Здравствуйте, %s!\s
                Для подтверждения учетной записи перейдите по ссылке:\s
                %s""", userName, link);
    }


    public String generateCode(int codeLength) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < codeLength; i++) {
            stringBuilder.append(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));
        }
        return stringBuilder.toString();
    }



}
