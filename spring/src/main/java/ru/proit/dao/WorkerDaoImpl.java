package ru.proit.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.proit.spring.generated.tables.daos.WorkerDao;

@Repository
public class WorkerDaoImpl extends WorkerDao {

    private final DSLContext jooq;

    public WorkerDaoImpl(DSLContext jooq) {
        super(jooq.configuration());
        this.jooq = jooq;
    }
}
