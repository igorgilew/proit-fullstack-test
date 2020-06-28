package ru.proit.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.proit.spring.generated.Sequences;
import ru.proit.spring.generated.tables.daos.OrganizationDao;
import ru.proit.spring.generated.tables.pojos.Organization;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.proit.spring.generated.tables.Organization.ORGANIZATION;
import static ru.proit.spring.generated.tables.Worker.WORKER;


@Repository
public class OrganizationDaoImpl extends OrganizationDao {
    private final DSLContext jooq;

    public OrganizationDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Organization getActiveByIdd(Integer idd){
        return jooq.select(ORGANIZATION.fields())
                .from(ORGANIZATION)
                .where(ORGANIZATION.IDD.eq(idd).and(ORGANIZATION.DELETE_DATE.isNull()))
                .fetchOneInto(Organization.class);
    }

    public void create(Organization org){
        org.setId(jooq.nextval(Sequences.ORGANIZATION_ID_SEQ));

        if(org.getIdd() == null){
            org.setIdd(org.getId());
        }

        org.setCreateDate(LocalDateTime.now());
        super.insert(org);
    }

    public Integer getCountActiveChildrenOrgByIdd(Integer idd){
        return jooq.selectCount()
                .from(ORGANIZATION).where(ORGANIZATION.HEAD_IDD.eq(idd).and(ORGANIZATION.DELETE_DATE.isNull()))
                .fetchOne(0, Integer.class);
    }

    public List<Organization> getAllActiveHeadOrg(){
        return jooq.select(ORGANIZATION.fields())
                .from(ORGANIZATION)
                .where(ORGANIZATION.DELETE_DATE.isNull().and(ORGANIZATION.HEAD_IDD.isNull()))
                .fetchInto(Organization.class);
    }

    public List<Organization> getAllActiveByHeadIdd(Integer headIdd){
        return jooq.select(ORGANIZATION.fields())
                .from(ORGANIZATION)
                .where(ORGANIZATION.HEAD_IDD.eq(headIdd).and(ORGANIZATION.DELETE_DATE.isNull()))
                .fetchInto(Organization.class);
    }

}
