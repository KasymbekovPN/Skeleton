package org.KasymbekovPN.Skeleton.lib.processing.context.ids;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// TODO: 22.11.2020 test
public class SKMultiContextIds<T> implements MultiContextIds<T> {

    private final ContextIds defaultContextIds;
    private final Map<T, ContextIds> contextIdsMap;

    private T key;

    private SKMultiContextIds(ContextIds defaultContextIds, Map<T, ContextIds> contextIdsMap) {
        this.defaultContextIds = defaultContextIds;
        this.contextIdsMap = contextIdsMap;
    }

    @Override
    public void setKey(T key) {
        this.key = key;
    }

    @Override
    public Iterator<String> taskIterator() {
        return getCurrentContextIds().taskIterator();
    }

    @Override
    public Iterator<String> handlerIterator() {
        return getCurrentContextIds().handlerIterator();
    }

    private ContextIds getCurrentContextIds(){
        return key == null || !contextIdsMap.containsKey(key)
                ? defaultContextIds
                : contextIdsMap.get(key);
    }

    public static class Builder<T>{

        private final ContextIds defaultContextIds;
        private Map<T, ContextIds> contextIdsMap = new HashMap<>();

        public Builder(ContextIds defaultContextIds) {
            this.defaultContextIds = defaultContextIds;
        }

        public Builder<T> add(T key, ContextIds contextIds){
            contextIdsMap.put(key, contextIds);
            return this;
        }

        public SKMultiContextIds<T> build(){
            return new SKMultiContextIds<T>(defaultContextIds, contextIdsMap);
        }
    }
}
