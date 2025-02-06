package by.it_academy.jd2.user_service.dao.projection;

import by.it_academy.jd2.user_service.dao.entity.UserRole;
import by.it_academy.jd2.user_service.dao.entity.UserStatus;

import java.util.UUID;

public interface UserLoginProjection {
    UUID getUserId();

    String getPassword();

    UserRole getRole();

    UserStatus getStatus();
}
