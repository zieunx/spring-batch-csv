package com.danal.publicdataprocessor.util;

import java.util.Arrays;

public enum YnType {
    Y,N;

    static public YnType valueOfOrNull(String yn) {
        return Arrays.stream(YnType.values())
                .findFirst()
                .orElse(null);
    }

    public boolean toBoolean() {
        return switch (this) {
            case Y -> true;
            case N -> false;
        };
    }
}
