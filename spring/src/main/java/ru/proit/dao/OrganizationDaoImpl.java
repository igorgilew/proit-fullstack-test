package ru.proit.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.proit.spring.generated.Sequences;
import ru.proit.spring.generated.tables.daos.OrganizationDao;
import ru.proit.spring.generated.tables.pojos.Organization;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Repository
public class OrganizationDaoImpl extends OrganizationDao {
    private final DSLContext jooq;

    public OrganizationDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public void create(Organization org){
        org.setId(jooq.nextval(Sequences.ORGANIZATION_ID_SEQ));

        if(org.getIdd() == null){
            org.setIdd(org.getId());
        }

        org.setCreateDate(LocalDateTime.now());
        super.insert(org);
    }

}
