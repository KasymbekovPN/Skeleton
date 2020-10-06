package org.KasymbekovPN.Skeleton.lib.result;

import java.lang.reflect.InvocationTargetException;

/**
 * Implementations of this interface MUST contain constructor without parameters
 */
public interface SimpleResult extends Result {
    boolean isSuccess();
    String getStatus();
    void setSuccess(boolean success);
    void setStatus(String status);
    ResultData getResultData() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    SimpleResult createInstance();
}
