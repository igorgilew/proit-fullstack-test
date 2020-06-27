package ru.proit.dao;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.proit.spring.generated.tables.daos.OrganizationDao;

@Repository
public class OrganizationDaoImpl extends OrganizationDao {
    private final DSLContext jooq;

    public OrganizationDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }
}
