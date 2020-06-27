package ru.proit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.proit.dao.WorkerDaoImpl;

@Service
@AllArgsConstructor
public class WorkerService {
    private WorkerDaoImpl workerDao;


}
