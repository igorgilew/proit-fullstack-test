package ru.proit.dto.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganizationParams {
    private Integer idd;
    private String name;
    private String orderBy;
    private String orderDir;
}
