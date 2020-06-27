package ru.proit.dto.organization;

import lombok.Getter;
import lombok.Setter;
import ru.proit.dto.BaseHistoryDto;
import ru.proit.dto.worker.WorkerDto;
import ru.proit.spring.generated.tables.daos.OrganizationDao;

@Getter
@Setter
public class OrganizationHistoryDto extends BaseHistoryDto {
    private String name;
    //головная организация
    private OrganizationDao head;
}
