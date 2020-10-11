package org.KasymbekovPN.Skeleton.lib.result;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class SKAggregateResult implements AggregateResult {

    private final Map<String, Result> results = new HashMap<>();

    @Override
    public void put(String resultId, Result result) {
        results.put(resultId, result);
    }

    @Override
    public Result get(String resultId) {
        if (has(resultId)){
            throw new NoSuchElementException();
        }
        return results.get(resultId);
    }

    @Override
    public boolean has(String resultId) {
        return results.containsKey(resultId);
    }

    @Override
    public void reset() {
        results.clear();
    }
}
