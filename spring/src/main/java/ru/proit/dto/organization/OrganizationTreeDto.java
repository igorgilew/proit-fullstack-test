package ru.proit.dto.organization;

import lombok.Getter;
import lombok.Setter;
import ru.proit.dto.BaseListDto;

import java.util.List;

@Getter
@Setter
public class OrganizationTreeDto extends BaseListDto {
    private String name;
    //головная организация
    private OrganizationDto head;
    //дочерние организации
    private List<OrganizationTreeDto> children;
}
