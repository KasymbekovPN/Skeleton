package org.KasymbekovPN.Skeleton.lib.processing.context.ids;

import java.util.Iterator;
import java.util.Map;

// todo :test
public class SKMultiContextIds implements MultiContextIds {

    private final ContextIds defaultContextIds;
    private final Map<Object, ContextIds> contextIdsMap;

    private Object key;

    private SKMultiContextIds(ContextIds defaultContextIds, Map<Object, ContextIds> contextIdsMap) {
        this.defaultContextIds = defaultContextIds;
        this.contextIdsMap = contextIdsMap;
    }

    @Override
    public void setKey(Object key) {
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

    public static class Builder{

        private final ContextIds defaultContextIds;
        private Map<Object, ContextIds> contextIdsMap;

        public Builder(ContextIds defaultContextIds) {
            this.defaultContextIds = defaultContextIds;
        }

        public Builder add(Object key, ContextIds contextIds){
            contextIdsMap.put(key, contextIds);
            return this;
        }

        SKMultiContextIds build(){
            return new SKMultiContextIds(defaultContextIds, contextIdsMap);
        }
    }
}
