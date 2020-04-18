package de.s2d_advgui.singstar;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public final class SingStar {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final static SingStar INSTANCE = new SingStar();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static SingStar getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Reflections reflection;

    // -------------------------------------------------------------------------------------------------------------------------
    @NonNull
    private final Map<Class<?>, Object> singletons = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("rawtypes")
    private final Map<Object, IC_ClazzRegisterObjectContainer> owo = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("nls")
    private SingStar() {
        ConfigurationBuilder sd = new ConfigurationBuilder();
        sd.addUrls(ClasspathHelper.forPackage(""));
        this.reflection = new Reflections(sd);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @SuppressWarnings("unchecked")
    public <T extends ASingStarDescriptorString> IC_ClazzRegisterObjectContainer<T> getObjectContainer(
            @Nonnull Class<? extends T> clazz) {
        IC_ClazzRegisterObjectContainer<T> some = this.owo.get(clazz);
        if (some != null) return some;
        Map<String, T> m1 = new HashMap<>();
        for (Class<? extends T> s : this.reflection.getSubTypesOf(clazz)) {
            T si = this.getObject(s);
            if (si != null) {
                String jas = si.getID();
                m1.put(jas, si);
            }
        }
        some = new IC_ClazzRegisterObjectContainer<>(m1);
        this.owo.put(clazz, some);
        return some;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private <T extends Object> T getObject(@Nonnull Class<? extends T> clz) {
        try {
            if (clz.isInterface()) return null;
            if (Modifier.isAbstract(clz.getModifiers())) return null;

            Object was = this.singletons.get(clz);
            if (was != null) return clz.cast(was);

            try {
                Method method = clz.getMethod("getInstance"); //$NON-NLS-1$
                Object juppy = method.invoke(null);
                if (juppy != null) {
                    if (clz.isAssignableFrom(juppy.getClass())) {
                        this.singletons.put(clz, juppy);
                        return clz.cast(juppy);
                    }
                }
            } catch (@SuppressWarnings("unused") NoSuchMethodException e) {
                // DON
            }

            Object nwo = clz.getDeclaredConstructor().newInstance();
            this.singletons.put(clz, nwo);
            return clz.cast(nwo);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
