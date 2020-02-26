package de.s2d_advgui.core.input;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author lhilbert@communicode.de
 */
public class GuiUtils {
    // -------------------------------------------------------------------------------------------------------------------------
    public static boolean isEnter(char key) {
        return key == '\r' || key == '\n' || key == '\u0003';
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static boolean isVisible(Actor actor) {
        Actor curr = actor;
        while (curr != null) {
            if (!curr.isVisible()) {
                return false;
            }
            curr = curr.getParent();
        }
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
