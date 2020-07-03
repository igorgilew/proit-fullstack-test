package ru.proit.dao;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.jooq.DSLContext;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;
import ru.proit.dto.Page;
import ru.proit.dto.PageParams;
import ru.proit.dto.organization.OrganizationParams;
import ru.proit.spring.generated.tables.pojos.Organization;
import ru.proit.spring.generated.tables.records.OrganizationRecord;

import java.util.ArrayList;
import java.util.List;

import static ru.proit.spring.generated.tables.Organization.ORGANIZATION;

@Repository
@AllArgsConstructor
public class OrganizationListDao {

    private final DSLContext jooq;

    public Page<Organization> list(PageParams<OrganizationParams> pageParams){
        //получаем параметры будущего запроса
        final OrganizationParams params = pageParams.getParams() == null ? new OrganizationParams() : pageParams.getParams();

        val listQuery = getOrgSelect(params);

        val count = jooq.selectCount()
                .from(listQuery)
                .fetchOne(0, Long.class);

        List<Organization> list = listQuery.offset(pageParams.getStart())
                .limit(pageParams.getPage())
                .fetchInto(Organization.class);

        return new Page<>(list, count);
    }

    private SelectSeekStepN<OrganizationRecord> getOrgSelect (OrganizationParams params) {
        var condition = ORGANIZATION.DELETE_DATE.isNull();

        if(params.getName() != null && !params.getName().isEmpty()){
            condition = condition.and(ORGANIZATION.NAME.like(params.getName()));
        }

        val sort = getOrderBy(params.getOrderBy(), params.getOrderDir());

        return jooq.selectFrom(ORGANIZATION)
                .where(condition)
                .orderBy(sort);
    }

    private SortField[] getOrderBy(String orderBy, String orderDir){
        val asc = orderDir == null || orderDir.equalsIgnoreCase("asc");

        if (orderBy == null){
            return asc
                    ? new SortField[]{ORGANIZATION.IDD.asc()}
                    : new SortField[]{ORGANIZATION.IDD.desc()};
        }

        val orderArray = orderBy.split(",");

        List<SortField> listSortBy = new ArrayList<>();
        for (val order: orderArray){
            if (order.equalsIgnoreCase("idd")){
                listSortBy.add(asc ? ORGANIZATION.IDD.asc() : ORGANIZATION.IDD.desc());
            }
            if (order.equalsIgnoreCase("name")){
                listSortBy.add(asc ? ORGANIZATION.NAME.asc() : ORGANIZATION.NAME.desc());
            }
            if (order.equalsIgnoreCase("createDate")){
                listSortBy.add(asc ? ORGANIZATION.CREATE_DATE.asc() : ORGANIZATION.CREATE_DATE.desc());
            }
        }
        return listSortBy.toArray(new SortField[0]);
    }

}
