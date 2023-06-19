package com.comoozi.reptile.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingDTO {
    private int page;
    private int maxPage;
    private int startPage;
    private int endPage;
}
