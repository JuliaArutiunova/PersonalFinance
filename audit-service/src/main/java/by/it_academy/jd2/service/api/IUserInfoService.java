package by.it_academy.jd2.service.api;

import by.it_academy.lib.dto.UserInfoDto;

public interface IUserInfoService {
    void save(UserInfoDto userInfoDto);
    void update(UserInfoDto userInfoDto);
}
