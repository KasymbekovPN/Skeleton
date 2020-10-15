package org.KasymbekovPN.Skeleton.lib.processing.context.state;

import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;

public interface ContextStateMemento {
    void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    SimpleResult getValidationResult();
}
