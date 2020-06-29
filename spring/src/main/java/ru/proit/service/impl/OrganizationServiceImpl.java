package ru.proit.service.impl;

import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.proit.dao.OrganizationDaoImpl;
import ru.proit.dao.OrganizationListDao;
import ru.proit.dao.WorkerDaoImpl;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.organization.OrganizationDto;
import ru.proit.dto.organization.OrganizationListDto;
import ru.proit.dto.organization.OrganizationParams;
import ru.proit.dto.organization.OrganizationTreeDto;
import ru.proit.mapping.MappingService;
import ru.proit.service.OrganizationService;
import ru.proit.spring.generated.tables.pojos.Organization;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationDaoImpl orgDao;
    private OrganizationListDao orgListDao;
    private MappingService mappingService;
    private WorkerDaoImpl workerDao;


    public Page<OrganizationListDto> getOrgsByParams(PageParams<OrganizationParams> pageParams) {
        Page<Organization> page = orgListDao.list(pageParams);
        List<OrganizationListDto> list = mappingService.mapList(page.getList(), OrganizationListDto.class);
        list.forEach(org->org.setCountWorkers(workerDao.getCountWorkersByOrgIdd(org.getIdd())));
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(OrganizationDto organizationDto) {
        orgDao.create(mappingService.map(organizationDto, Organization.class));
    }

    @Transactional
    public OrganizationDto update(Integer idd, OrganizationDto organizationDto) {
        Organization org = orgDao.getActiveByIdd(idd);

        if(org == null){
            //сделать свои exceptions
            throw new RuntimeException("");
        }

        org.setDeleteDate(LocalDateTime.now());
        orgDao.update(org);

        Organization newOrg = mappingService.map(organizationDto, Organization.class);
        newOrg.setIdd(org.getIdd());

        orgDao.create(newOrg);

        OrganizationDto orgDto = mappingService.map(newOrg, OrganizationDto.class);
        orgDto.setHead(mappingService.map(orgDao.getActiveByIdd(newOrg.getHeadIdd()), OrganizationDto.class));

        return orgDto;
    }

    @Transactional
    public void delete(Integer idd) {
        Organization org = orgDao.getActiveByIdd(idd);


        if(orgDao.getCountActiveChildrenOrgByIdd(org.getIdd())>0){
            //кидать свой эксепшн
            throw new RuntimeException("");
        }

        if(workerDao.getCountWorkersByOrgIdd(org.getIdd())>0){
            throw new RuntimeException("");
        }

        org.setDeleteDate(LocalDateTime.now());
        orgDao.update(org);
    }

    public List<OrganizationTreeDto> getOrgTree() {
        var allActiveHeadOrg = mappingService.mapList(orgDao.getAllActiveHeadOrg(), OrganizationTreeDto.class);
        allActiveHeadOrg.forEach(this::getChildren);
        return allActiveHeadOrg;
    }

    private void getChildren(OrganizationTreeDto org){
        var children = mappingService.mapList(orgDao.getAllActiveByHeadIdd(org.getIdd()), OrganizationTreeDto.class);
        if(children.size()==0) return;
        org.setChildren(children);
        children.forEach(child->{
            child.getHead().setName(org.getName());
            child.getHead().setCreateDate(org.getCreateDate());
            getChildren(child);
        });

    }

}
