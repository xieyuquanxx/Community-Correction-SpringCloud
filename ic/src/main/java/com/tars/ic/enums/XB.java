package com.tars.ic.enums;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public enum XB {
    MALE("0", "男"),
    FEMALE("1", "女");
    private final String code;
    private final String value;

    public static String map(String code) {
        if (Objects.equals(code, "0")) return MALE.value;
        else return FEMALE.value;
    }
}
