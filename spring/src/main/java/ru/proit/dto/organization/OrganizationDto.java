package ru.proit.dto.organization;

import lombok.Getter;
import lombok.Setter;
import ru.proit.dto.BaseDto;
import ru.proit.dto.worker.WorkerListDto;
import ru.proit.exception.EntityIllegalArgumentException;

import java.util.List;

@Getter
@Setter
public class OrganizationDto extends BaseDto<OrganizationHistoryDto> {
    private String name;
    //головная организация
    private OrganizationDto head;
    //дочерние организации
    private List<OrganizationTreeDto> children;
    //список сотрудников
    private List<WorkerListDto> workers;

}
