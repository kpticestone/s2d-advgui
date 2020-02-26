package de.s2d_advgui.singstar;

import de.s2d_advgui.commons.IOrderSupport;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class SingStarFinder {
    private static final String METHODNAME_GETINSTANCE = "getInstance"; //$NON-NLS-1$

    public static Collection<ISingStarAutowire> findSingStars(Reflections reflections) {
        List<ISingStarAutowire> back = new ArrayList<>();
        for (Class<? extends ISingStarAutowire> clazz : reflections.getSubTypesOf(ISingStarAutowire.class)) {
            if (!Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers())) {
                ISingStarAutowire singleton = getSingleton(clazz);
                if (singleton != null) {
                    back.add(singleton);
                }
            }
        }
        back.sort(Comparator.comparingInt(o -> {
            if (o instanceof IOrderSupport) {
                return ((IOrderSupport) o).getOrderId();
            }
            return Integer.MAX_VALUE;
        }));
        return back;
    }

    private static ISingStarAutowire getSingleton(Class<? extends ISingStarAutowire> x) {
        if (x.isEnum()) {
            return null;
        }
        try {
            try {
                Method me = x.getMethod(METHODNAME_GETINSTANCE);
                return (ISingStarAutowire) me.invoke(null);
            } catch (@SuppressWarnings("unused") NoSuchMethodException e) {
                Constructor<? extends ISingStarAutowire> cnst = x.getDeclaredConstructor();
                cnst.setAccessible(true);
                return cnst.newInstance();
            }
        } catch (Throwable e) {
            System.err.println("error on class " + x);
            e.printStackTrace();
        }
        return null;
    }
}
