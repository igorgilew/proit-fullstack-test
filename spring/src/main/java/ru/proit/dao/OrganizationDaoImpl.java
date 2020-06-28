package ru.proit.dao;

import lombok.val;
import lombok.var;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.organization.OrganizationParams;
import ru.proit.spring.generated.tables.daos.OrganizationDao;
import ru.proit.spring.generated.tables.pojos.Organization;
import ru.proit.spring.generated.tables.records.OrganizationRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.proit.spring.generated.tables.Organization.ORGANIZATION;


@Repository
public class OrganizationDaoImpl extends OrganizationDao {
    private final DSLContext jooq;

    public OrganizationDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }



}
