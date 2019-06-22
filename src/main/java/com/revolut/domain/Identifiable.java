package com.revolut.domain;

public interface Identifiable {
    long INVALID_ID = -1L;

    Long getId();

    default boolean isValid() {
        return INVALID_ID != getId();
    }
}
