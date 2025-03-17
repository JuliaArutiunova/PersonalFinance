package by.it_academy.jd2.service.feign;

import by.it_academy.lib.dto.ActionInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "audit-service", url =  "${client.audit-service.url}")
public interface AuditClient {

    @PostMapping("/audit_data/audit_create")
    void createAudit(@RequestBody ActionInfoDto actionInfoDto);
}
