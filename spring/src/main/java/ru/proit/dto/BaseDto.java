package ru.proit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseDto <HistoryDto extends BaseHistoryDto> extends BaseListDto implements Serializable {
    private List<HistoryDto> history;
}
