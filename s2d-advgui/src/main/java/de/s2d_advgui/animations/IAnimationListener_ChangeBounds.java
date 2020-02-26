package de.s2d_advgui.animations;

import com.badlogic.gdx.math.Rectangle;

@FunctionalInterface
public interface IAnimationListener_ChangeBounds {
	void onChange(Rectangle pRect);
}
