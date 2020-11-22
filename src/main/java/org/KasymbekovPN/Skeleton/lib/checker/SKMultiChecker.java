package org.KasymbekovPN.Skeleton.lib.checker;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SKMultiChecker<K, T> implements MultiChecker<K, T> {

    private final SimpleChecker<T> defaultSimpleChecker;
    private final Map<K, SimpleChecker<T>> simpleCheckers;

    private SKMultiChecker(SimpleChecker<T> defaultSimpleChecker, Map<K, SimpleChecker<T>> simpleCheckers) {
        this.defaultSimpleChecker = defaultSimpleChecker;
        this.simpleCheckers = simpleCheckers;
    }

    @Override
    public Optional<K> checkByAll(T checkableValue) {
        for (Map.Entry<K, SimpleChecker<T>> entry : simpleCheckers.entrySet()) {
            if (entry.getValue().check(checkableValue)){
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean check(T checkableValue) {
        return check(null, checkableValue);
    }

    @Override
    public boolean check(K key, T checkableValue) {
        return key == null || !simpleCheckers.containsKey(key)
                ? defaultSimpleChecker.check(checkableValue)
                : simpleCheckers.get(key).check(checkableValue);
    }

    public static class Builder<K, T>{
        private final SimpleChecker<T> defaultSimpleChecker;
        private Map<K, SimpleChecker<T>> simpleCheckers = new HashMap<>();

        public Builder(SimpleChecker<T> defaultSimpleChecker) {
            this.defaultSimpleChecker = defaultSimpleChecker;
        }

        public Builder<K, T> add(K key, SimpleChecker<T> checker){
            simpleCheckers.put(key, checker);
            return this;
        }

        public MultiChecker<K, T> build(){
            return new SKMultiChecker<>(defaultSimpleChecker, simpleCheckers);
        }
    }
}
