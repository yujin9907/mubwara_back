package site.metacoding.finals.config.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Saled {
    SALE("sale"),
    NONE("none");

    private final String value;

}
