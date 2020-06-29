package ru.proit.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.jooq.*;
import org.springframework.stereotype.Repository;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.organization.OrganizationParams;
import ru.proit.dto.worker.WorkerListDto;
import ru.proit.dto.worker.WorkerParams;
import ru.proit.spring.generated.tables.pojos.Organization;
import ru.proit.spring.generated.tables.pojos.Worker;
import ru.proit.spring.generated.tables.records.OrganizationRecord;
import ru.proit.spring.generated.tables.records.WorkerRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.proit.spring.generated.tables.Organization.ORGANIZATION;
import static ru.proit.spring.generated.tables.Worker.WORKER;

@Repository
@AllArgsConstructor
public class WorkerListDao {

    private final DSLContext jooq;


    public Page<Worker> list(PageParams<WorkerParams> pageParams) {
        final WorkerParams params = pageParams.getParams() == null ? new WorkerParams() : pageParams.getParams();

        val listQuery = getWorkersSelect(params);



        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Worker> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Worker.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<Record> getWorkersSelect (WorkerParams params) {
        var condition = WORKER.DELETE_DATE.isNull();

        if(params.getFirstName() != null){
            condition = condition.and(WORKER.FIRST_NAME.like(params.getFirstName()));
        }

        if(params.getSecondName() != null){
            condition = condition.and(WORKER.SECOND_NAME.like(params.getSecondName()));
        }


        if(params.getOrgName() != null){
            condition = condition.and(ORGANIZATION.NAME.like(params.getOrgName()));

            //condition = condition.or(ORGANIZATION.NAME.like(params.getOrgName()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.select(WORKER.fields())
                .from(WORKER.join(ORGANIZATION)
                        .on(WORKER.ORG_IDD.eq(ORGANIZATION.IDD)))
                        .where(condition)
                        .orderBy(sort);
    }


    private SortField[] getOrderBy(String orderBy, String orderDir){
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null){
            return asc
                    ? new SortField[]{WORKER.IDD.asc()}
                    : new SortField[]{WORKER.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray){
            if (order.equalsIgnoreCase("idd")){
                listSortBy.add(asc ? WORKER.IDD.asc() : WORKER.IDD.desc());
            }
            if (order.equalsIgnoreCase("first_name")){
                listSortBy.add(asc ? WORKER.FIRST_NAME.asc() : WORKER.FIRST_NAME.desc());
            }
            if (order.equalsIgnoreCase("second_name")){
                listSortBy.add(asc ? WORKER.SECOND_NAME.asc() : WORKER.SECOND_NAME.desc());
            }
            if (order.equalsIgnoreCase("createDate")){
                listSortBy.add(asc ? WORKER.CREATE_DATE.asc() : WORKER.CREATE_DATE.desc());
            }
        }
        return listSortBy.toArray(new SortField[0]);
    }

}
