package ru.proit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.proit.dao.OrganizationDaoImpl;
import ru.proit.dao.WorkerDaoImpl;
import ru.proit.dao.WorkerListDao;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.organization.OrganizationDto;
import ru.proit.dto.organization.OrganizationListDto;
import ru.proit.dto.worker.WorkerDto;
import ru.proit.dto.worker.WorkerListDto;
import ru.proit.dto.worker.WorkerParams;
import ru.proit.mapping.MappingService;
import ru.proit.spring.generated.tables.pojos.Organization;
import ru.proit.spring.generated.tables.pojos.Worker;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkerService {
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
}
