package ru.proit.service;

import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.worker.WorkerDto;
import ru.proit.dto.worker.WorkerListDto;
import ru.proit.dto.worker.WorkerParams;
import ru.proit.dto.worker.WorkerTreeDto;

import java.util.List;

public interface WorkerService {
    Page<WorkerListDto> getWorkersByParams(PageParams<WorkerParams> pageParams);

    void create(WorkerDto workerDto);

    WorkerDto update(Integer idd, WorkerDto workerDto);

    void delete(Integer idd);

    List<WorkerTreeDto> getWorkersTree();
}
