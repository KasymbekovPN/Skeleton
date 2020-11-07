package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state;

import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;

import java.util.Map;

public interface InstanceContextStateMemento extends ContextStateMemento {
    String getClassName();
    Number getClassModifiers();
    Map<String, Object> getFieldValues(String kind);
    Map<String, String> getAnnotationNames(String kind);
}
