package ru.proit.dto.worker;

import lombok.Getter;
import lombok.Setter;
import ru.proit.dto.BaseListDto;
import ru.proit.dto.organization.OrganizationDto;

@Getter
@Setter
public class WorkerListDto extends BaseListDto {
    private String firstName;
    private String secondName;
    private OrganizationDto orgIdd;
    private WorkerDto bossIdd;
}
