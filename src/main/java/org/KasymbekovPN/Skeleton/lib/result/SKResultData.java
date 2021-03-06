package org.KasymbekovPN.Skeleton.lib.result;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SKResultData implements ResultData {

    private final Map<String, Object> data = new HashMap<>();

    public SKResultData() {}

    @Override
    public void set(String key, Object value) {
        if (!data.containsKey(key)){
            data.put(key, value);
        }
    }

    @Override
    public Object get(String key, Class<?> clazz) {
        if (!contains(key, clazz)){
            throw new NoSuchElementException();
        }

        return data.get(key);
    }

    @Override
    public boolean contains(String key, Class<?> clazz) {
        return data.containsKey(key) && data.get(key).getClass().equals(clazz);
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SKResultData that = (SKResultData) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
