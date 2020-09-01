package org.KasymbekovPN.Skeleton.custom.result.serialization.instance.processor;

import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.BaseResultImpl;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.HashMap;
import java.util.Map;

public class InstanceProcessorResult extends BaseResultImpl implements AggregateResult {

    private final Map<String, Result> results = new HashMap<>();
    private final Result dummy;

    public InstanceProcessorResult(Result dummy) {
        super();
        this.dummy = dummy;
    }

    @Override
    public void put(String resultId, Result result) {
        results.put(resultId, result);
    }

    @Override
    public Result get(String resultId) {
        return results.getOrDefault(resultId, dummy);
    }

    @Override
    public void reset() {
        super.reset();
        if (results != null){
            results.clear();
        }
    }

    @Override
    public Result createNew() {
        return new InstanceProcessorResult(dummy.createNew());
    }
}