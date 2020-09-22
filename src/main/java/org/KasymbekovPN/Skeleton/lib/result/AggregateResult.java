package org.KasymbekovPN.Skeleton.lib.result;

public interface AggregateResult extends Result {
    void put(String resultId, Result result);
    boolean has(String resultId);
    Result get(String resultId);
}
