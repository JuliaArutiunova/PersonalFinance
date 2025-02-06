package by.it_academy.jd2.user_service.service.api;


import by.it_academy.jd2.user_service.dto.UserCreateDto;
import by.it_academy.jd2.user_service.dto.UserDto;
import by.it_academy.jd2.user_service.dto.UserLoginDto;
import by.it_academy.jd2.user_service.dto.VerificationDto;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.dto.TokenInfoDto;

import java.util.UUID;

public interface IUserService {
    void create(UserCreateDto userCreateDto);

    PageDto<UserDto> getUsersPage(int pageNumber, int size);

    UserDto getById(UUID id);

    void update(UUID uuid, long dtUpdate, UserCreateDto userCreateDTO);

    void verify(VerificationDto verificationDto);

    TokenInfoDto getTokenInfo(UserLoginDto loginDTO);

    UserDto getMe();

}
