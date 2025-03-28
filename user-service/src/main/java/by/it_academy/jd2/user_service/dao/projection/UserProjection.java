package by.it_academy.jd2.user_service.dao.projection;


import by.it_academy.jd2.user_service.dao.entity.UserStatus;
import by.it_academy.lib.enums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserProjection {
    UUID getUserId();

    LocalDateTime getDtCreate();

    LocalDateTime getDtUpdate();

    String getMail();

    String getFio();

    UserRole getRole();

    UserStatus getStatus();
}
