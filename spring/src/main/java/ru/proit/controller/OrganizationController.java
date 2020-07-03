package ru.proit.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.organization.OrganizationDto;
import ru.proit.dto.organization.OrganizationListDto;
import ru.proit.dto.organization.OrganizationParams;
import ru.proit.dto.organization.OrganizationTreeDto;
import ru.proit.service.OrganizationService;
import ru.proit.service.impl.OrganizationServiceImpl;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/org", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class OrganizationController {

    private OrganizationService orgService;

    @PostMapping("/list")
    public Page<OrganizationListDto> getList(@RequestBody PageParams<OrganizationParams> pageParams){
        return orgService.getOrgsByParams(pageParams);
    }

    @GetMapping("/list/all")
    public Page<OrganizationListDto> getAll(){
        return orgService.getAll();
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

    @GetMapping("/tree")
    public List<OrganizationTreeDto> getOrgTree(){
        return orgService.getOrgTree();
    }

    @GetMapping("/{idd}")
    public OrganizationDto getOrgByIdd(@PathVariable("idd") Integer idd) {
        return orgService.getOrgByIdd(idd);
    }

}
