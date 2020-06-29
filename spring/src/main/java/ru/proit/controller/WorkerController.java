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
import ru.proit.dto.worker.WorkerListDto;
import ru.proit.dto.worker.WorkerParams;
import ru.proit.service.WorkerService;

@RestController
@RequestMapping(value = "/workers", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class WorkerController {

    private WorkerService workerService;

    @PostMapping("/list")
    public Page<WorkerListDto> getList(@RequestBody PageParams<WorkerParams> pageParams){
        return workerService.getWorkersByParams(pageParams);
    }

}
