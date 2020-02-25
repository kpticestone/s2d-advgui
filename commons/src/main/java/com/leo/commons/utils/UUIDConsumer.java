package com.leo.commons.utils;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface UUIDConsumer {
    // -------------------------------------------------------------------------------------------------------------------------
    void accept(@Nonnull UUID pUid);

    // -------------------------------------------------------------------------------------------------------------------------
}
