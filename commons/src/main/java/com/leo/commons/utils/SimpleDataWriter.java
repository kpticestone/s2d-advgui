package com.leo.commons.utils;

import java.io.Closeable;
import java.io.OutputStream;

public final class SimpleDataWriter extends OutputStream implements Closeable {
    // -------------------------------------------------------------------------------------------------------------------------
    private final byte[] arr;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Trigger closable;
    
    // -------------------------------------------------------------------------------------------------------------------------
    private int offset = 0;

    // -------------------------------------------------------------------------------------------------------------------------
    public SimpleDataWriter(byte[] oneRowBuffer) {
        this.arr = oneRowBuffer;
        this.closable = null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SimpleDataWriter(byte[] oneRowBuffer, Trigger pClosable) {
        this.arr = oneRowBuffer;
        this.closable = pClosable;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void writeString(int max, String pUid) {
        if (pUid != null) {
            byte[] bytes = pUid.getBytes();
            System.arraycopy(bytes, 0, this.arr, this.offset, Math.min(bytes.length, max));
        }
        this.offset += max;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void close() {
        if (this.closable != null) {
            this.closable.onTrigger();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void writeDouble(double x) {
        writeLong(Double.doubleToLongBits(x));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void writeFloat(float v) {
        writeInt(Float.floatToIntBits(v));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void writeLong(long v) {
        this.arr[this.offset++] = (byte) (v >>> 56);
        this.arr[this.offset++] = (byte) (v >>> 48);
        this.arr[this.offset++] = (byte) (v >>> 40);
        this.arr[this.offset++] = (byte) (v >>> 32);
        this.arr[this.offset++] = (byte) (v >>> 24);
        this.arr[this.offset++] = (byte) (v >>> 16);
        this.arr[this.offset++] = (byte) (v >>> 8);
        this.arr[this.offset++] = (byte) (v);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void writeInt(int v) {
        this.arr[this.offset++] = (byte) (v >>> 24);
        this.arr[this.offset++] = (byte) (v >>> 16);
        this.arr[this.offset++] = (byte) (v >>> 8);
        this.arr[this.offset++] = (byte) (v);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void writeBoolean(boolean b) {
        this.arr[this.offset++] = b ? (byte) 1 : (byte) 0;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void write(int b) {
        this.arr[this.offset++] = (byte) (b);
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
}
