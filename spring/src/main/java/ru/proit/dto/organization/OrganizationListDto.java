package ru.proit.dto.organization;

import lombok.Getter;
import lombok.Setter;
import ru.proit.dto.BaseListDto;

@Getter
@Setter
public class OrganizationListDto extends BaseListDto {
    private String name;
    private Integer countWorkers;
}
