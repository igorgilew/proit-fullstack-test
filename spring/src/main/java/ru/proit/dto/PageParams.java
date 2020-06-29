package ru.proit.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class PageParams<T> implements Serializable {
    private int start;
    private int page = 5;
    private T params;
}
