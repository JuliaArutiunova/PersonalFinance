package by.it_academy.jd2.user_service.service.client;

import by.it_academy.lib.dto.ActionInfoDto;
import by.it_academy.lib.dto.UserActionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "audit-service", url = "${client.audit-service.url}")
public interface AuditClient {
    @PostMapping("/audit_data/user_create")
    void saveUserInfo(@RequestBody UserActionDto userActionDto);
    @PutMapping("/audit_data/user_update")
    void updateUser(@RequestBody UserActionDto userActionDto);
    @PostMapping("/audit_data/audit_create")
    void createAudit(@RequestBody ActionInfoDto actionInfoDto);
}
