package by.it_academy.jd2.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserHolder {
    public UUID getUserId(){
        return (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
