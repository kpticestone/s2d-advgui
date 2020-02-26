package de.s2d_advgui.core.rendering;

public interface ISwtBatchSaver extends AutoCloseable {
    @Override
    void close();
}
