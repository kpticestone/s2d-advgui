package de.s2d_advgui.core.window;

import java.util.HashMap;
import java.util.Map;

import de.s2d_advgui.commons.TypeSafeID;

public class WindowID extends TypeSafeID {
    // -------------------------------------------------------------------------------------------------------------------------
    private static final Map<String, WindowID> INSTANCES = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public static WindowID get(String id) {
        WindowID type = INSTANCES.get(id);
        if (type == null) {
            throw new IllegalArgumentException(String.format("WindowID %s does not exist", id)); //$NON-NLS-1$
        }
        return type;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    public static WindowID create(String id) {
        return new WindowID(id);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private WindowID(String id) {
        super(id);
        if (INSTANCES.containsKey(id)) {
            throw new IllegalStateException(String.format("%s already created!", id)); //$NON-NLS-1$
        }
        INSTANCES.put(id, this);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
