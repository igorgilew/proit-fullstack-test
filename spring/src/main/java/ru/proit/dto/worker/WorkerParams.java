package ru.proit.dto.worker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkerParams {
    private Integer idd;
    private String firstName;
    private String secondName;
    private String orgName;
    private String orderBy;
    private String orderDir;
}
