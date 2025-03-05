package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.api.IUserInfoDao;
import by.it_academy.jd2.service.api.IUserInfoService;
import by.it_academy.lib.dto.UserInfoDto;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements IUserInfoService {
    private final IUserInfoDao userInfoDao;

    public UserInfoService(IUserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    @Override
    public void save(UserInfoDto userInfoDto) {

    }

    @Override
    public void update(UserInfoDto userInfoDto) {

    }
}
