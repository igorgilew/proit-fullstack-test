package ru.proit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.proit.dao.OrganizationDaoImpl;

@Service
@AllArgsConstructor
public class OrganizationService {
    private OrganizationDaoImpl orgDao;

}
