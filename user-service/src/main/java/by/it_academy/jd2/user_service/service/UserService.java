package by.it_academy.jd2.user_service.service;


import by.it_academy.jd2.user_service.dto.UserCreateDto;
import by.it_academy.jd2.user_service.dto.UserDto;
import by.it_academy.jd2.user_service.dto.UserLoginDto;
import by.it_academy.jd2.user_service.dto.VerificationDto;
import by.it_academy.jd2.user_service.exception.ActivationException;
import by.it_academy.jd2.user_service.exception.CodeNotValidException;
import by.it_academy.lib.exception.DataChangedException;
import by.it_academy.jd2.user_service.exception.PasswordNotValidException;
import by.it_academy.jd2.user_service.service.api.IUserService;
import by.it_academy.jd2.user_service.service.api.IVerificationService;
import by.it_academy.jd2.user_service.dao.api.IUserDao;
import by.it_academy.jd2.user_service.dao.entity.UserEntity;
import by.it_academy.jd2.user_service.dao.entity.UserRole;
import by.it_academy.jd2.user_service.dao.entity.UserStatus;
import by.it_academy.jd2.user_service.dao.entity.VerificationEntity;
import by.it_academy.jd2.user_service.dao.projection.UserLoginProjection;
import by.it_academy.jd2.user_service.dao.projection.UserProjection;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.dto.TokenInfoDto;
import by.it_academy.lib.exception.PageNotExistsException;
import by.it_academy.lib.exception.RecordAlreadyExistsException;
import by.it_academy.lib.exception.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.*;


@Service
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final IVerificationService verificationService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;
    private final UserHolder userHolder;


    public UserService(IUserDao userDao, IVerificationService verificationService,
                       ModelMapper modelMapper, PasswordEncoder encoder, UserHolder userHolder) {
        this.userDao = userDao;
        this.verificationService = verificationService;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.userHolder = userHolder;
    }

    @Override
    @Transactional
    public void create(UserCreateDto userCreateDto) {
        String userMail = userCreateDto.getMail();

        if (userDao.existsByMail(userMail)) {
            throw new RecordAlreadyExistsException("Пользователь с email " + userMail
                    + " уже существует");
        }

        UserEntity userEntity = UserEntity.builder()
                .userId(UUID.randomUUID())
                .fio(userCreateDto.getFio())
                .mail(userMail)
                .password(encoder.encode(userCreateDto.getPassword()))
                .status(UserStatus.valueOf(userCreateDto.getStatus()))
                .role(UserRole.valueOf(userCreateDto.getRole()))
                .build();

        userDao.saveAndFlush(userEntity);

        if (userEntity.getStatus().equals(UserStatus.WAITING_ACTIVATION)) {
            verificationService.create(userEntity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<UserDto> getUsersPage(int pageNumber, int size) {
        Page<UserProjection> page = userDao.findAllProjectedBy(PageRequest.of(pageNumber, size));

        if (!page.hasPrevious() && pageNumber > 0) {
            throw new PageNotExistsException("Страницы с номером " + pageNumber + " не существует");
        }

        if (page.isEmpty()){
            return new PageDto<>();
        }

        return modelMapper.map(page, new TypeToken<PageDto<UserDto>>() {
        }.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(UUID id) {

        UserProjection userProjection = userDao.findUserProjectionByUserId(id).orElseThrow(() ->
                new RecordNotFoundException("Пользователь не найден"));

        return modelMapper.map(userProjection, UserDto.class);
    }

    @Override
    @Transactional
    public void update(UUID uuid, long dtUpdate, UserCreateDto userCreateDTO) {

        UserEntity userEntity = userDao.findById(uuid).orElseThrow(() ->
                new RecordNotFoundException("Пользователь не найден"));

        if (userEntity.getDtUpdate().toEpochSecond(ZoneOffset.UTC) != dtUpdate) {
            throw new DataChangedException();
        }

        userEntity.setMail(userCreateDTO.getMail());
        userEntity.setFio(userCreateDTO.getFio());
        userEntity.setRole(UserRole.valueOf(userCreateDTO.getRole()));
        userEntity.setStatus(UserStatus.valueOf(userCreateDTO.getStatus()));
        userEntity.setPassword(encoder.encode(userCreateDTO.getPassword()));

        userDao.saveAndFlush(userEntity);

    }

    @Override
    @Transactional
    public void verify(VerificationDto verificationDto) {
        String mail = verificationDto.getMail();

        if (!userDao.existsByMail(mail)) {
            throw new RecordNotFoundException("Пользователь с email " + mail + " не найден");
        }

        VerificationEntity verification = verificationService.get(mail);

        if (verification.getCode().equals(verificationDto.getCode())) {
            UserEntity userEntity = verification.getUserEntity();
            userEntity.setStatus(UserStatus.ACTIVATED);
            userDao.saveAndFlush(userEntity);
            verificationService.delete(verification);

        } else {
            throw new CodeNotValidException("Неверный код верификации");
        }
    }

    @Override
    @Transactional
    public TokenInfoDto getTokenInfo(UserLoginDto loginDTO) {

        UserLoginProjection userInfo = userDao.findUserLoginProjectionByMail(loginDTO.getMail()).orElseThrow(() ->
                new RecordNotFoundException("Пользователь с email " + loginDTO.getMail() + " не найден"));

        if (!userInfo.getStatus().equals(UserStatus.ACTIVATED)) {
            throw new ActivationException("Аккаунт не активирован. Необходимо пройти верификацию");
        }

        if (!encoder.matches(loginDTO.getPassword(), userInfo.getPassword())) {
            throw new PasswordNotValidException("Неверный пароль");
        }

        return new TokenInfoDto(userInfo.getUserId().toString(), userInfo.getRole().name());
    }


    @Override
    @Transactional(readOnly = true)
    public UserDto getMe() {
        UUID id = userHolder.getUserId();
        return getById(id);
    }


}
