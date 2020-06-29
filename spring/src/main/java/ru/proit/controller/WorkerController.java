package ru.proit.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.worker.WorkerDto;
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

    @PostMapping
    public void create(@RequestBody WorkerDto workerDto){
        workerService.create(workerDto);
    }

    @PatchMapping("/{idd}")
    public WorkerDto update(@PathVariable Integer idd,
                       @RequestBody WorkerDto workerDto) {
        return workerService.update(idd, workerDto);
    }

}