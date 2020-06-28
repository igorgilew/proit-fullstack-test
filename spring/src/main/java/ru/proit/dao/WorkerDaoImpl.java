package ru.proit.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.proit.spring.generated.tables.daos.WorkerDao;

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

}
