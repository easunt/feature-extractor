package com.ovio.extractor.enums;

import lombok.Getter;

@Getter
public enum BadTerms {
    A("aaaa", 1),
    B("bbbb", 2);

    private String value;
    private int code;

    BadTerms(String value, int code) {
        this.value = value;
        this.code = code;
    }
}
