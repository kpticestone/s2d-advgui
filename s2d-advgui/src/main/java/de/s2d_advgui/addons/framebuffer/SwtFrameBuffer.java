package de.s2d_advgui.addons.framebuffer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class SwtFrameBuffer extends FrameBuffer {
    // -------------------------------------------------------------------------------------------------------------------------
    private TextureRegion region;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtFrameBuffer(FrameBufferBuilder frameBufferBuilder) {
        super(frameBufferBuilder);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected Texture createTexture(FrameBufferTextureAttachmentSpec attachmentSpec) {
        Texture texture = super.createTexture(attachmentSpec);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        this.region = new TextureRegion(texture);
        this.region.flip(false, true);
        return texture;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public TextureRegion getRegion() {
        return this.region;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
