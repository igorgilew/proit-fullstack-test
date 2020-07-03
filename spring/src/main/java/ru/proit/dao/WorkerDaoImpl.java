package ru.proit.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.worker.WorkerParams;
import ru.proit.spring.generated.Sequences;
import ru.proit.spring.generated.tables.daos.WorkerDao;
import ru.proit.spring.generated.tables.pojos.Organization;
import ru.proit.spring.generated.tables.pojos.Worker;

import java.time.LocalDateTime;
import java.util.List;

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
                .from(WORKER).where(WORKER.ORG_IDD.eq(idd)
                        .and(WORKER.DELETE_DATE.isNull()))
                .fetchOne(0, Integer.class);
    }

    public Worker getActiveWorkerByIdd(Integer idd) {
        return jooq.select(WORKER.fields())
                .from(WORKER)
                .where(WORKER.IDD.eq(idd)
                        .and(WORKER.DELETE_DATE.isNull()))
                .fetchOneInto(Worker.class);
    }

    public void create(Worker worker) {
        worker.setId(jooq.nextval(Sequences.WORKER_ID_SEQ));

        if(worker.getIdd() == null){
            worker.setIdd(worker.getId());
        }

        worker.setCreateDate(LocalDateTime.now());
        super.insert(worker);
    }

    public Integer isBossOfWorkerHeadOfSameOrg(Integer headIdd, Integer orgIdd){
        return jooq.selectCount()
                .from(WORKER)
                .where(WORKER.ORG_IDD.eq(orgIdd)
                        .and(WORKER.IDD.eq(headIdd))
                        .and(WORKER.DELETE_DATE.isNull()))
                .fetchOne(0, Integer.class);
    }

    //проверка на то, есть ли у работника подчиненные
    public boolean isWorkerHasSubject(Integer idd){
        Integer countSubject = jooq.selectCount()
                .from(WORKER)
                .where(WORKER.BOSS_IDD.eq(idd).and(WORKER.DELETE_DATE.isNull()))
                .fetchOne(0, Integer.class);
        return countSubject != 0;
    }

    public List<Worker> getAllActiveBosses() {
        return jooq.select(WORKER.fields())
                .from(WORKER)
                .where(WORKER.DELETE_DATE.isNull().and(WORKER.BOSS_IDD.isNull()))
                .fetchInto(Worker.class);
    }

    public List<Worker> getAllActiveByBossIdd(Integer bossIdd) {
        return jooq.select(WORKER.fields())
                .from(WORKER)
                .where(WORKER.BOSS_IDD.eq(bossIdd).and(WORKER.DELETE_DATE.isNull()))
                .fetchInto(Worker.class);
    }

    public List<Worker> findAllActiveByOrgIdd(Integer orgIdd) {
        return jooq.select(WORKER.fields())
                .from(WORKER).where(WORKER.ORG_IDD.eq(orgIdd)
                        .and(WORKER.DELETE_DATE.isNull()))
                .fetchInto(Worker.class);
    }
}
