package ru.proit.dto.organization;

import lombok.Getter;
import lombok.Setter;
import ru.proit.dto.BaseDto;
import ru.proit.dto.worker.WorkerDto;
import ru.proit.spring.generated.tables.daos.OrganizationDao;

import java.util.List;

@Getter
@Setter
public class OrganizationDto extends BaseDto<OrganizationHistoryDto> {
    private String name;
    //головная организация
    private OrganizationDao head;

    //дочерние организации
    private List<OrganizationDao> children;
}
