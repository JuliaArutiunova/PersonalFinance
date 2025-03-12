package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.api.IUserInfoDao;
import by.it_academy.jd2.dao.entity.UserInfoEntity;
import by.it_academy.jd2.service.api.IUserInfoService;
import by.it_academy.lib.dto.UserInfoDto;
import by.it_academy.lib.exception.RecordAlreadyExistsException;
import by.it_academy.lib.exception.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserInfoService implements IUserInfoService {
    private final IUserInfoDao userInfoDao;
    private final ModelMapper modelMapper;

    public UserInfoService(IUserInfoDao userInfoDao, ModelMapper modelMapper) {
        this.userInfoDao = userInfoDao;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void save(UserInfoDto userInfoDto) {

        if (userInfoDao.existsById(userInfoDto.getUserId())) {
            throw new RecordAlreadyExistsException("Пользователь уже зарегистрирован в системе");
        }

        UserInfoEntity userInfoEntity = modelMapper.map(userInfoDto, UserInfoEntity.class);
        userInfoDao.saveAndFlush(userInfoEntity);
    }

    @Override
    @Transactional
    public void update(UserInfoDto userInfoDto) {

        UserInfoEntity userInfoEntity = getById(userInfoDto.getUserId());

        userInfoEntity.setMail(userInfoDto.getMail());
        userInfoEntity.setFio(userInfoDto.getFio());
        userInfoEntity.setUserRole(userInfoDto.getUserRole());

        userInfoDao.saveAndFlush(userInfoEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoEntity getById(UUID userId) {
        return userInfoDao.findById(userId).orElseThrow(() ->
                new RecordNotFoundException("Информация о пользователе не найдена"));
    }
}
