package org.KasymbekovPN.Skeleton.lib.processing.context.state;

import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.lang.reflect.InvocationTargetException;

public interface ContextStateMemento {
    void validate() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    Result getValidationResult();
}
