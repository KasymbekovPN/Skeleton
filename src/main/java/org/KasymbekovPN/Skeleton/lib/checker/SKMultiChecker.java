package org.KasymbekovPN.Skeleton.lib.checker;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class SKMultiChecker<K, T> implements MultiChecker<K, T> {

    private final Function<T, Boolean> defaultSimpleChecker;
    private final Map<K, Function<T, Boolean>> simpleCheckers;

    private SKMultiChecker(Function<T, Boolean> defaultSimpleChecker, Map<K, Function<T, Boolean>> simpleCheckers) {
        this.defaultSimpleChecker = defaultSimpleChecker;
        this.simpleCheckers = simpleCheckers;
    }

    @Override
    public boolean apply(K key, T checkableValue) {
        return key == null || !simpleCheckers.containsKey(key)
                ? defaultSimpleChecker.apply(checkableValue)
                : simpleCheckers.get(key).apply(checkableValue);
    }

    @Override
    public Optional<K> applyByAll(T checkableValue) {
        for (Map.Entry<K, Function<T, Boolean>> entry : simpleCheckers.entrySet()) {
            if (entry.getValue().apply(checkableValue)){
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    @Override
    public Boolean apply(T t) {
        return apply(null, t);
    }

    public static class Builder<K, T>{
        private final Function<T, Boolean> defaultSimpleChecker;
        private final Map<K, Function<T, Boolean>> simpleCheckers = new HashMap<>();

        public Builder(Function<T, Boolean> defaultSimpleChecker) {
            this.defaultSimpleChecker = defaultSimpleChecker;
        }

        public Builder<K, T> add(K key, Function<T, Boolean> checker){
            simpleCheckers.put(key, checker);
            return this;
        }

        public MultiChecker<K, T> build(){
            return new SKMultiChecker<>(defaultSimpleChecker, simpleCheckers);
        }
    }
}
