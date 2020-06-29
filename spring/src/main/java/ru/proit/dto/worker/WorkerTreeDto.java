package ru.proit.dto.worker;

import lombok.Getter;
import lombok.Setter;
import ru.proit.dto.BaseListDto;
import ru.proit.dto.organization.OrganizationDto;

import java.util.List;

@Getter
@Setter
public class WorkerTreeDto extends BaseListDto {
    private String firstName;
    private String secondName;
    private WorkerDto bossIdd;

    private List<WorkerTreeDto> children;
}
