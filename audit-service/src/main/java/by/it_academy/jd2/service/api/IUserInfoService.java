package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.UserInfoEntity;
import by.it_academy.lib.dto.UserInfoDto;

import java.util.UUID;

public interface IUserInfoService {
    void save(UserInfoDto userInfoDto);
    void update(UserInfoDto userInfoDto);
    UserInfoEntity getById(UUID userId);
}
