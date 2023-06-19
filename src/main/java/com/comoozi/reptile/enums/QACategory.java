package com.comoozi.reptile.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QACategory {
    GOODS("상품"),
    DELIVERY("배송"),
    PAYMENT("입금"),
    ETC("기타");

    private final String name;
}
