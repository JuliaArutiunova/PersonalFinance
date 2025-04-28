package by.it_academy.jd2.user_service.aop;

import by.it_academy.jd2.user_service.dao.entity.UserEntity;
import by.it_academy.jd2.user_service.dto.UserCreateDto;
import by.it_academy.jd2.user_service.dto.UserLoginDto;
import by.it_academy.jd2.user_service.dto.VerificationDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @Pointcut("execution(* by.it_academy.jd2.user_service.service.UserService.sendUserCreationAudit(by.it_academy.jd2.user_service.dao.entity.UserEntity)) && args(userEntity))")
    public void sendingUserCreationAudit(UserEntity userEntity) {
    }

    @Pointcut("execution(* by.it_academy.jd2.user_service.service.UserService.sendUserUpdateAudit(by.it_academy.jd2.user_service.dao.entity.UserEntity, boolean)) && args(userEntity,*))")
    public void sendingUserUpdateAudit(UserEntity userEntity) {
    }

    @Pointcut("execution(* by.it_academy.jd2.user_service.service.VerificationService.create(by.it_academy.jd2.user_service.dao.entity.UserEntity)) && args(userEntity))")
    public void creatingVerification(UserEntity userEntity) {
    }

    @Pointcut("execution(* by.it_academy.jd2.user_service.service.UserService.create(by.it_academy.jd2.user_service.dto.UserCreateDto)) && args(userCreateDto))")
    public void creatingUser(UserCreateDto userCreateDto) {
    }

    @Pointcut("execution(* by.it_academy.jd2.user_service.service.UserService.update(java.util.UUID,..)) && args(uuid, ..))")
    public void updatingUser(UUID uuid) {
    }

    @Pointcut("execution(* by.it_academy.jd2.user_service.service.UserService.verify(by.it_academy.jd2.user_service.dto.VerificationDto)) && args(verificationDto))")
    public void verificationUser(VerificationDto verificationDto) {
    }

    @Pointcut("execution(* by.it_academy.jd2.user_service.service.MailSenderService.sendMail(String,String)) && args(to,..))")
    public void sendingMail(String to) {
    }

    @Pointcut("execution(* by.it_academy.jd2.user_service.service.UserService.getTokenInfo(by.it_academy.jd2.user_service.dto.UserLoginDto)) && args(userLoginDto))")
    public void gettingTokenInfo(UserLoginDto userLoginDto) {
    }

    @AfterReturning("gettingTokenInfo(userLoginDto)")
    public void logAfterGettingToken(UserLoginDto userLoginDto) {
        log.info("Успешное получение информации для токена пользователя с email {}", userLoginDto.getMail());

    }

    @AfterThrowing(value = "gettingTokenInfo(userLoginDto)", throwing = "exception")
    public void logErrorGettingTokenInfo(UserLoginDto userLoginDto, Throwable exception) {
        log.warn("Ошибка получения информации для токена для {}: {}",userLoginDto.getMail(), exception.getMessage());
    }

    @Before("sendingMail(to)")
    public void logSendingMail(String to) {
        log.info("Отправка email на адрес {}", to);
    }

    @AfterThrowing(value = "sendingMail(to)", throwing = "exception")
    public void logErrorSendingMail(String to, MailException exception) {
        log.error("Ошибка отправки email на адрес {}", to, exception);
    }

    @AfterReturning("verificationUser(verificationDto)")
    public void logVerificationUser(VerificationDto verificationDto) {
        log.info("Пользователь с email: {} прошел верификацию", verificationDto.getMail());
    }

    @AfterThrowing(value = "verificationUser(verificationDto)", throwing = "exception")
    public void logErrorUserVerification(VerificationDto verificationDto, Throwable exception) {
        log.warn("Ошибка верификации пользователя с email: {} : {}",
                verificationDto.getMail(), exception.getMessage());
    }

    @AfterReturning("updatingUser(uuid)")
    public void logUpdateUser(UUID uuid) {
        log.info("Обновлены данные пользователя с id: {}", uuid);
    }

    @AfterReturning("creatingUser(userCreateDto)")
    public void logCreateUser(UserCreateDto userCreateDto) {
        log.info("Создан пользователь с email: {}", userCreateDto.getMail());
    }

    @AfterReturning("creatingVerification(userEntity)")
    public void logCreateVerification(UserEntity userEntity) {
        log.info("Созданы данные для верификации пользователя с email: {}", userEntity.getMail());
    }

    @Before("sendingUserUpdateAudit(userEntity)")
    public void logSendingUserUpdateAudit(UserEntity userEntity) {
        log.info("Отправлены обновленные данные пользователя с id = {} в audit-service", userEntity.getUserId());

    }

    @Before("sendingUserCreationAudit(userEntity)")
    public void logSendingUserCreationAudit(UserEntity userEntity) {
        log.info("Отправлены данные пользователя с id = {} в audit-service", userEntity.getUserId());
    }


}
