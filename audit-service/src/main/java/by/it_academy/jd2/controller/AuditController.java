package by.it_academy.jd2.controller;

import by.it_academy.jd2.dto.AuditDto;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.dto.PaginationDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/audit")
public class AuditController {
    private final IAuditService auditService;

    public AuditController(IAuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public PageDto<AuditDto> getPage(@Valid PaginationDto paginationDto) {
        return auditService.getPage(paginationDto);
    }

    @GetMapping("/{uuid}")
    public AuditDto getAudit(@PathVariable("uuid") UUID uuid) {
        return auditService.get(uuid);
    }
}
