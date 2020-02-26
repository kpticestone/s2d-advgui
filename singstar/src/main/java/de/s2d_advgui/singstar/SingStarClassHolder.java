package de.s2d_advgui.singstar;

import com.google.common.collect.Sets;
import de.s2d_advgui.commons.LeoNoNameException;
import de.s2d_advgui.commons.Primary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class SingStarClassHolder {
    private static final Logger log = LoggerFactory.getLogger(SingStarClassHolder.class);

    private Map<Class<?>, ClassEntry<Object>> internal = new HashMap<>();
    private Set<Object> instances = new HashSet<>();
    private static final Set<Class> IGNORE_CLASSES = Collections.unmodifiableSet(Sets.<Class>newHashSet(
            ISingStarDescriptor.class, ISingStarAutowire.class
    ));

    public void put(Object instance) {
        instances.add(instance);
        Class<?> clazz = instance.getClass();
        boolean isPrimary = clazz.isAnnotationPresent(Primary.class);

        forEachSuperclass(clazz, superClass -> {
            if (!IGNORE_CLASSES.contains(superClass)) {
                internal.computeIfAbsent(superClass, ClassEntry::new).add(instance, isPrimary);
            }
        });
    }

    private void forEachSuperclass(Class ofClass, Consumer<Class> consumer) {
        Class currentClass = ofClass;
        while (currentClass != Object.class) {
            consumer.accept(currentClass);
            forEachInterface(currentClass, consumer);
            currentClass = currentClass.getSuperclass();
        }
    }

    private void forEachInterface(Class<?> clazz, Consumer<Class> consumer) {
        for (Class<?> currInterface : clazz.getInterfaces()) {
            consumer.accept(currInterface);
            forEachInterface(currInterface, consumer);
        }
    }

    public <T> ISingStarCollection<T> getAll(Class<T> clazz) {
        return getInternal(clazz).instances;
    }

    public <T> T getOne(Class<T> clazz) {
        ClassEntry<T> entry = getInternal(clazz);
        if (entry.primary != null) {
            return entry.primary;
        }
        if (entry.instances.size() != 1) {
            throw new LeoNoNameException(String.format("%s wirerings found for %s", entry.instances.size(), clazz));
        }
        return entry.instances.iterator().next();
    }

    public boolean containsKey(Class toWire) {
        return internal.containsKey(toWire);
    }

    private <T> ClassEntry<T> getInternal(Class<T> clazz) {
        ClassEntry entry = internal.get(clazz);
        if (entry == null) {
            throw new NoSuchElementException(String.format("no singleton for %s exists", clazz));
        }
        //noinspection unchecked
        return (ClassEntry<T>) entry;
    }

    public void forEach(Consumer<Object> c) {
        instances.forEach(c);
    }

    public static class ClassEntry<T> {
        private T primary = null;
        private SingStarCollection<T> instances = new SingStarCollection<>();
        private Class<?> forClass;

        public ClassEntry(Class<?> forClass) {
            this.forClass = forClass;
        }

        private void add(T instance, boolean isPrimary) {
            T previous = instances.add(instance);
            if (previous != null) {
                log.error("Duplicated Handlers for '{}': {} <-> {}",
                        forClass.getSimpleName(),
                        instance.getClass().getSimpleName(),
                        previous.getClass().getSimpleName()
                );
            }
            if (isPrimary) {
                this.primary = instance;
            }
        }
    }

    private static class SingStarCollection<T> implements ISingStarCollection<T> {
        Set<T> use = new LinkedHashSet<>();
        Map<Object, T> idMap = new HashMap<>();

        private T add(T instance) {
            if (use.add(instance)) {
                if (instance instanceof ISingStarDescriptor) {
                    return this.idMap.put(((ISingStarDescriptor) instance).getID(), instance);
                }
            }
            return null;
        }

        @Nonnull
        @Override
        public Iterator<T> iterator() {
            return use.iterator();
        }

        @Override
        public int size() {
            return use.size();
        }

        @Override
        public Optional<T> get(Object id) {
            return Optional.ofNullable(idMap.get(id));
        }
    }
}
