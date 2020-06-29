package ru.proit.dto.worker;

import lombok.Getter;
import lombok.Setter;
import ru.proit.dto.BaseDto;
import ru.proit.dto.organization.OrganizationDto;

import java.util.List;

@Getter
@Setter
public class WorkerDto extends BaseDto<WorkerHistoryDto> {
    private String firstName;
    private String secondName;
    private OrganizationDto orgIdd;
    private WorkerDto bossIdd;

    private List<WorkerHistoryDto> history;
    private List<WorkerListDto> children;

}
