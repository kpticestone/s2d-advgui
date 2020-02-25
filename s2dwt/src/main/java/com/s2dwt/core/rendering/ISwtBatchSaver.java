package com.s2dwt.core.rendering;

public interface ISwtBatchSaver extends AutoCloseable {
    @Override
    void close();
}
