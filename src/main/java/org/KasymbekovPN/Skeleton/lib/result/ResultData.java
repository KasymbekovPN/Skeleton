package org.KasymbekovPN.Skeleton.lib.result;

public interface ResultData {
    void set(String key, Object value);
    Object get(String key, Class<?> clazz);
    boolean contains(String key, Class<?> clazz);
    void clear();
    ResultData createInstance();
}
