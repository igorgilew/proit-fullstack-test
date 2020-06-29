package ru.proit.service.impl;

import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.proit.dao.OrganizationDaoImpl;
import ru.proit.dao.WorkerDaoImpl;
import ru.proit.dao.WorkerListDao;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.organization.OrganizationDto;
import ru.proit.dto.organization.OrganizationTreeDto;
import ru.proit.dto.worker.WorkerDto;
import ru.proit.dto.worker.WorkerListDto;
import ru.proit.dto.worker.WorkerParams;
import ru.proit.dto.worker.WorkerTreeDto;
import ru.proit.mapping.MappingService;
import ru.proit.service.WorkerService;
import ru.proit.spring.generated.tables.pojos.Organization;
import ru.proit.spring.generated.tables.pojos.Worker;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private WorkerDaoImpl workerDao;
    private WorkerListDao workerListDao;
    private OrganizationDaoImpl orgDao;
    private MappingService mappingService;

    public Page<WorkerListDto> getWorkersByParams(PageParams<WorkerParams> pageParams) {
        Page<Worker> page = workerListDao.list(pageParams);
        List<WorkerListDto> list = mappingService.mapList(page.getList(), WorkerListDto.class);
        //list.forEach(org->org.setCountWorkers(workerDao.getCountWorkersByOrgIdd(org.getIdd())));
        list.forEach(
                worker->
                {
                    worker.setOrgIdd(mappingService.map(orgDao.getActiveByIdd(worker.getOrgIdd().getIdd()), OrganizationDto.class));
                    if(worker.getBossIdd() != null){
                        worker.setBossIdd(mappingService.map(workerDao.getActiveWorkerByIdd(worker.getBossIdd().getIdd()), WorkerDto.class));
                    }
                });
        return new Page<>(list, page.getTotalCount());
    }

    @Transactional
    public void create(WorkerDto workerDto) {
        //сделать свои эксепшены
        if(workerDto.getBossIdd() != null &&
                workerDao.isBossOfWorkerHeadOfSameOrg(workerDto.getBossIdd().getIdd(),
                        workerDto.getOrgIdd().getIdd()) == 0)
            throw new RuntimeException("");

        workerDao.create(mappingService.map(workerDto, Worker.class));
    }

    @Transactional
    public WorkerDto update(Integer idd, WorkerDto workerDto) {

        Worker worker = workerDao.getActiveWorkerByIdd(idd);

        if(worker == null) throw new RuntimeException("");

        //сделать свои эксепшены
        if(workerDto.getBossIdd() != null &&
                workerDao.isBossOfWorkerHeadOfSameOrg(workerDto.getBossIdd().getIdd(),
                        workerDto.getOrgIdd().getIdd()) == 0)
            throw new RuntimeException("");

        //все подчиненные должны быть из одной организации,
        // а мы можем поменять организацию у руководителя
        if(workerDao.isWorkerHasSubject(worker.getIdd())) throw new RuntimeException("");

        //все проверки пройдены
        worker.setDeleteDate(LocalDateTime.now());

        workerDao.update(worker);

        Worker newWorker = mappingService.map(workerDto, Worker.class);
        newWorker.setIdd(worker.getIdd());

        workerDao.create(newWorker);

        WorkerDto updatedWorkerDto = mappingService.map(newWorker, WorkerDto.class);
        updatedWorkerDto.setOrgIdd(mappingService.map(orgDao.getActiveByIdd(newWorker.getOrgIdd()), OrganizationDto.class));

        if(newWorker.getBossIdd() != null){
            updatedWorkerDto.setBossIdd(mappingService.map(workerDao.getActiveWorkerByIdd(newWorker.getBossIdd()), WorkerDto.class));
        }

        return updatedWorkerDto;
    }

    @Transactional
    public void delete(Integer idd) {
        Worker worker = workerDao.getActiveWorkerByIdd(idd);

        if(workerDao.isWorkerHasSubject(idd)){
            //кидать свой эксепшн
            throw new RuntimeException("");
        }

        worker.setDeleteDate(LocalDateTime.now());
        workerDao.update(worker);
    }

    public List<WorkerTreeDto> getWorkersTree() {
        var allActiveBosses = mappingService.mapList(workerDao.getAllActiveBosses(), WorkerTreeDto.class);
        allActiveBosses.forEach(this::getChildren);
        return allActiveBosses;
    }

    private void getChildren(WorkerTreeDto boss){
        var children = mappingService.mapList(workerDao.getAllActiveByBossIdd(boss.getIdd()), WorkerTreeDto.class);

        if(children.size()==0) return;
        boss.setChildren(children);
        children.forEach(child->{
            child.getBossIdd().setFirstName(boss.getFirstName());
            child.getBossIdd().setSecondName(boss.getSecondName());
            child.getBossIdd().setCreateDate(boss.getCreateDate());
            getChildren(child);
        });

    }


}
