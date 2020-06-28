package ru.proit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Page<T> implements Serializable {
    private List<T> list;
    private Long totalCount;
}
