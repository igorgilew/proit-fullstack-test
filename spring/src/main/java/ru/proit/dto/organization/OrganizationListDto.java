package ru.proit.dto.organization;

import lombok.Getter;
import lombok.Setter;
import ru.proit.dto.BaseListDto;
import ru.proit.dto.worker.WorkerDto;

@Getter
@Setter
public class OrganizationListDto extends BaseListDto {
    private String name;
}
