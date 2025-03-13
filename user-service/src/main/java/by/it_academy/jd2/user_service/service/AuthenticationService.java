package by.it_academy.jd2.user_service.service;

import by.it_academy.jd2.user_service.controller.utils.JwtTokenHandler;
import by.it_academy.jd2.user_service.dto.UserCreateDto;
import by.it_academy.jd2.user_service.dto.UserLoginDto;
import by.it_academy.jd2.user_service.dto.UserRegistrationDto;
import by.it_academy.jd2.user_service.service.api.IAuthenticationService;
import by.it_academy.jd2.user_service.service.api.IUserService;
import by.it_academy.jd2.user_service.dao.entity.UserStatus;
import by.it_academy.lib.dto.TokenInfoDto;
import by.it_academy.lib.enums.UserRole;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final IUserService userService;
    private final JwtTokenHandler jwtTokenHandler;

    public AuthenticationService(IUserService userService,JwtTokenHandler jwtTokenHandler) {
        this.userService = userService;
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @Override
    public void registerUser(UserRegistrationDto registrationDTO) {
        userService.create(UserCreateDto.builder()
                .mail(registrationDTO.getMail())
                .fio(registrationDTO.getFio())
                .password(registrationDTO.getPassword())
                .role(UserRole.USER.name())
                .status(UserStatus.WAITING_ACTIVATION.name())
                .build());
    }

    @Override
    public String getToken(UserLoginDto loginDTO) {
        TokenInfoDto tokenInfo = userService.getTokenInfo(loginDTO);
        return  jwtTokenHandler.generateToken(tokenInfo);
    }

}
