package by.it_academy.jd2.user_service.service.api;

import by.it_academy.jd2.user_service.dto.UserLoginDto;
import by.it_academy.jd2.user_service.dto.UserRegistrationDto;

public interface IAuthenticationService {
    void registerUser(UserRegistrationDto registrationDTO);

    String getToken(UserLoginDto loginDTO);

}
