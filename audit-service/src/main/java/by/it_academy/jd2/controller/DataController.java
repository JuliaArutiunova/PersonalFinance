package by.it_academy.jd2.controller;


import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.IUserInfoService;
import by.it_academy.lib.dto.ActionInfoDto;
import by.it_academy.lib.dto.UserInfoDto;
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
    public void createUserInfo(@RequestBody UserInfoDto userInfoDto) {
        userInfoService.save(userInfoDto);
    }
    @PutMapping("/user_update")
    public void updateUserInfo(@RequestBody UserInfoDto userInfoDto){
        userInfoService.update(userInfoDto);
    }

    @PostMapping("/audit_create")
    public void createAudit(@RequestBody ActionInfoDto actionInfoDto) {
        auditService.create(actionInfoDto);
    }
}
