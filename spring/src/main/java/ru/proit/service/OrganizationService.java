package ru.proit.service;

import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.organization.OrganizationDto;
import ru.proit.dto.organization.OrganizationListDto;
import ru.proit.dto.organization.OrganizationParams;
import ru.proit.dto.organization.OrganizationTreeDto;

import java.util.List;

public interface OrganizationService {

    Page<OrganizationListDto> getOrgsByParams(PageParams<OrganizationParams> pageParams);

    void create(OrganizationDto organizationDto);

    OrganizationDto update(Integer idd, OrganizationDto organizationDto);

    void delete(Integer idd);

    List<OrganizationTreeDto> getOrgTree();
}
