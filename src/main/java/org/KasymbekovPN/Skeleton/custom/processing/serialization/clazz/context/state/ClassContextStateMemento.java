package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state;

import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;

import java.lang.reflect.Field;
import java.util.Set;

public interface ClassContextStateMemento extends ContextStateMemento {
    Class<?> getClazz();
    Set<Field> getRemainingFields();
}
