package by.it_academy.jd2.controller;


import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.IUserInfoService;
import by.it_academy.lib.dto.ActionInfoDto;
import by.it_academy.lib.dto.UserActionDto;
import by.it_academy.lib.dto.UserInfoDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audit_data")
public class DataController {
    private final IUserInfoService userInfoService;
    private final IAuditService auditService;

    public DataController(IUserInfoService userInfoService, IAuditService auditService) {
        this.userInfoService = userInfoService;
        this.auditService = auditService;
    }

    @PostMapping("/user_create")
    public void createUserInfo(@Valid @RequestBody UserActionDto userInfoDto) {
        userInfoService.save(userInfoDto.getUserInfoDto());
        auditService.create(userInfoDto.getActionInfoDto());
    }
    @PutMapping("/user_update")
    public void updateUserInfo(@Valid @RequestBody UserActionDto userActionDto){
        userInfoService.update(userActionDto.getUserInfoDto());
        auditService.create(userActionDto.getActionInfoDto());
    }

    @PostMapping("/audit_create")
    public void createAudit(@Valid @RequestBody ActionInfoDto actionInfoDto) {
        auditService.create(actionInfoDto);
    }
}
