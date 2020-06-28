package ru.proit.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.organization.OrganizationListDto;
import ru.proit.dto.organization.OrganizationParams;
import ru.proit.service.OrganizationService;

@RestController
@RequestMapping(value = "/org", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class OrganizationController {

    private OrganizationService orgService;

    @PostMapping("/list")
    public Page<OrganizationListDto> getList(@RequestBody PageParams<OrganizationParams> pageParams){
        return orgService.getOrgsByParams(pageParams);
    }

}
