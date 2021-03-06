package ru.proit.service.impl;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.proit.annotation.Loggable;
import ru.proit.dao.OrganizationDaoImpl;
import ru.proit.dao.OrganizationListDao;
import ru.proit.dao.WorkerDaoImpl;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.organization.OrganizationDto;
import ru.proit.dto.organization.OrganizationListDto;
import ru.proit.dto.organization.OrganizationParams;
import ru.proit.dto.organization.OrganizationTreeDto;
import ru.proit.exception.EntityHasDetailsException;
import ru.proit.exception.EntityIllegalArgumentException;
import ru.proit.exception.EntityNotFoundException;
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


    @Loggable
    public Page<OrganizationListDto> getOrgsByParams(PageParams<OrganizationParams> pageParams) {
        Page<Organization> page = orgListDao.list(pageParams);
        List<OrganizationListDto> list = mappingService.mapList(page.getList(), OrganizationListDto.class);
        list.forEach(org->org.setCountWorkers(workerDao.getCountWorkersByOrgIdd(org.getIdd())));
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(OrganizationDto organizationDto) {

        validateOrgDto(organizationDto);

        orgDao.create(mappingService.map(organizationDto, Organization.class));
    }

    private void validateOrgDto(OrganizationDto organizationDto){
        if(organizationDto.getName() == null || organizationDto.getName().isEmpty()){
            throw new EntityIllegalArgumentException("Организация должна иметь имя");
        }


        if(organizationDto.getHead() != null){

            if(organizationDto.getHead().getIdd() == null){
                throw new EntityIllegalArgumentException("Головная организация должна иметь idd");
            }

            Organization organization = orgDao.getActiveByIdd(organizationDto.getHead().getIdd());
            if(organization == null){
                throw new EntityNotFoundException("Organization", organizationDto.getHead().getIdd());
            }
        }


    }

    @Loggable
    @Transactional
    public OrganizationDto update(Integer idd, OrganizationDto organizationDto) {
        Organization org = orgDao.getActiveByIdd(idd);

        if(org == null){

            throw new EntityNotFoundException("Organization", idd);
        }
        //проверяем изменеия
        validateOrgDto(organizationDto);

        if(orgDao.getCountActiveChildrenOrgByIdd(org.getIdd())>0){

            throw new EntityHasDetailsException("Удаляемая организация имеет дочерние организации");
        }


        //помечаем старую запись как удаленную
        org.setDeleteDate(LocalDateTime.now());
        orgDao.update(org);

        //создаем новую запись
        Organization newOrg = mappingService.map(organizationDto, Organization.class);
        newOrg.setIdd(org.getIdd());

        orgDao.create(newOrg);

        //создаем новую дто в ответ
        OrganizationDto orgDto = mappingService.map(newOrg, OrganizationDto.class);
        orgDto.setHead(mappingService.map(orgDao.getActiveByIdd(newOrg.getHeadIdd()), OrganizationDto.class));

        return orgDto;
    }

    @Transactional
    public void delete(Integer idd) {
        Organization org = orgDao.getActiveByIdd(idd);

        if(org == null){
            throw new EntityNotFoundException("Organization", idd);
        }

        if(orgDao.getCountActiveChildrenOrgByIdd(org.getIdd())>0){

            throw new EntityHasDetailsException("Удаляемая организация имеет дочерние организации");
        }

        if(workerDao.getCountWorkersByOrgIdd(org.getIdd())>0){
            throw new EntityHasDetailsException("Удаляемая организация имеет сотрудников");

        }

        org.setDeleteDate(LocalDateTime.now());
        orgDao.update(org);
    }

    @Loggable
    public List<OrganizationTreeDto> getOrgTree() {
        var allActiveHeadOrg = mappingService.mapList(orgDao.getAllActiveHeadOrg(), OrganizationTreeDto.class);
        allActiveHeadOrg.forEach(this::getChildren);
        return allActiveHeadOrg;
    }

    @Override
    public Page<OrganizationListDto> getAll() {

        val list = mappingService.mapList(orgDao.findAllActive(), OrganizationListDto.class);
        return new Page<>(list, (long) list.size());
    }

    @Override
    public OrganizationDto getOrgByIdd(Integer idd) {
         Organization org = orgDao.getActiveByIdd(idd);

         if(org == null){
             throw new EntityNotFoundException("Организация", idd);
         }

         return mappingService.map(org);
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
