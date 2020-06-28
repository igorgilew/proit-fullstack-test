package ru.proit.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.organization.OrganizationDto;
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

    @PostMapping
    public void create(@RequestBody OrganizationDto organizationDto) {
        orgService.create(organizationDto);
    }

    @PatchMapping("/{idd}")
    public OrganizationDto update(@PathVariable("idd") Integer idd,
                                  @RequestBody OrganizationDto organizationDto) {
        return orgService.update(idd, organizationDto);
    }

    @DeleteMapping("/{idd}")
    public void delete(@PathVariable("idd") Integer idd){
        orgService.delete(idd);
    }

}
