package com.s2dwt.addons.framebuffer;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;

/**
 * @author lhilbert@communicode.de
 */
public class SwtFrameBufferBuilder extends GLFrameBuffer.FrameBufferBuilder {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtFrameBufferBuilder(Pixmap.Format format, int width, int height) {
        super(width, height);
        addBasicColorTextureAttachment(format);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public SwtFrameBuffer build() {
        return new SwtFrameBuffer(this);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
