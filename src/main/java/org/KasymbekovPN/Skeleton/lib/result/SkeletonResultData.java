package org.KasymbekovPN.Skeleton.lib.result;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class SkeletonResultData implements ResultData {

    private final Map<String, Object> data = new HashMap<>();

    public SkeletonResultData() {}

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
}
