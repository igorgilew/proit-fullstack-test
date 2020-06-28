package ru.proit.dto.worker;

import lombok.Getter;
import lombok.Setter;
import ru.proit.dto.BaseHistoryDto;
import ru.proit.dto.organization.OrganizationDto;

@Getter
@Setter
public class WorkerHistoryDto extends BaseHistoryDto {
    private String firstName;
    private String secondName;
    private OrganizationDto organization;
    private WorkerDto boss;
}
