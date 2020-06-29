package ru.proit.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.worker.WorkerParams;
import ru.proit.spring.generated.tables.daos.WorkerDao;
import ru.proit.spring.generated.tables.pojos.Organization;
import ru.proit.spring.generated.tables.pojos.Worker;

import static ru.proit.spring.generated.tables.Organization.ORGANIZATION;
import static ru.proit.spring.generated.tables.Worker.WORKER;

@Repository
public class WorkerDaoImpl extends WorkerDao {

    private final DSLContext jooq;

    public WorkerDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }

    public Integer getCountWorkersByOrgIdd(Integer idd){
        return jooq.selectCount()
                .from(WORKER).where(WORKER.ORG_IDD.eq(idd))
                .fetchOne(0, Integer.class);
    }

    public Worker getActiveWorkerByIdd(Integer idd) {
        return jooq.select(WORKER.fields())
                .from(WORKER)
                .where(WORKER.IDD.eq(idd).and(WORKER.DELETE_DATE.isNull()))
                .fetchOneInto(Worker.class);
    }

}
