package by.it_academy.jd2.user_service.service;

import by.it_academy.lib.exception.RecordNotFoundException;
import by.it_academy.jd2.user_service.service.api.IVerificationService;
import by.it_academy.jd2.user_service.service.utils.Generator;
import by.it_academy.jd2.user_service.dao.api.IVerificationDao;
import by.it_academy.jd2.user_service.dao.entity.UserEntity;
import by.it_academy.jd2.user_service.dao.entity.VerificationEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class VerificationService implements IVerificationService {

    private final Generator generator;
    private final MailSenderService mailSenderService;
    private final IVerificationDao verificationDao;


    public VerificationService(Generator generator, MailSenderService mailSenderService,
                               IVerificationDao verificationDao) {
        this.generator = generator;
        this.mailSenderService = mailSenderService;
        this.verificationDao = verificationDao;
    }

    @Override
    @Transactional 
    public void create(UserEntity userEntity) {
        String code = generator.generateCode(10);

        verificationDao.saveAndFlush(VerificationEntity.builder()
                .verificationId(UUID.randomUUID())
                .code(code)
                .userEntity(userEntity)
                .build());

        mailSenderService.sendMail(userEntity.getMail(),
                generator.generateVerificationMessageText(userEntity.getFio(), userEntity.getMail(), code));
        
    }

    @Override
    @Transactional(readOnly = true)
    public VerificationEntity get(String mail) {
        return verificationDao.findVerificationByUserMail(mail).orElseThrow(() ->
                new RecordNotFoundException("Данные для верификации пользователя не былы найдены"));
    }

    @Override
    @Transactional
    public void delete(VerificationEntity verification){
        verificationDao.delete(verification);
    }
}
