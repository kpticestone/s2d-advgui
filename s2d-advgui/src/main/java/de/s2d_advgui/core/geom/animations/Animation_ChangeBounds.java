package de.s2d_advgui.core.geom.animations;

import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.animations.AAnimation;

public class Animation_ChangeBounds extends AAnimation {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Rectangle src;
    private final Rectangle dst;
    private final Rectangle calc = new Rectangle();
    private IAnimationListener_ChangeBounds listener;

    // -------------------------------------------------------------------------------------------------------------------------
    public Animation_ChangeBounds(float startTime, float endTime, Rectangle src, Rectangle dst,
            IAnimationListener_ChangeBounds pListener) {
        super(startTime, endTime);
        this.src = src;
        this.dst = dst;
        this.listener = pListener;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void _calc(float ri) {
        if (ri >= 0) {
            this.calc.x = interpol(this.src.x, this.dst.x);
            this.calc.y = interpol(this.src.y, this.dst.y);
            this.calc.width = interpol(this.src.width, this.dst.width);
            this.calc.height = interpol(this.src.height, this.dst.height);
            this.listener.onChange(this.calc);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
