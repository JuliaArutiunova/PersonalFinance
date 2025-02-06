package by.it_academy.jd2.user_service.dao.api;

import by.it_academy.jd2.user_service.dao.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IVerificationDao extends JpaRepository<VerificationEntity, UUID> {
    @Query("SELECT v FROM VerificationEntity v WHERE v.userEntity.mail = :mail")
    Optional<VerificationEntity> findVerificationByUserMail(@Param("mail") String mail);
}
