package by.it_academy.jd2.user_service.service.api;

import by.it_academy.jd2.user_service.dao.entity.UserEntity;
import by.it_academy.jd2.user_service.dao.entity.VerificationEntity;

public interface IVerificationService {

    void create(UserEntity userEntity);

    VerificationEntity get(String mail);

    void delete(VerificationEntity verification);

}
