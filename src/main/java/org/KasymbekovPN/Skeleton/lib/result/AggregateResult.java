package org.KasymbekovPN.Skeleton.lib.result;

/**
 * Implementations of this interface MUST contain constructor without patameters
 */
public interface AggregateResult extends Result {
    void put(String resultId, Result result);
    boolean has(String resultId);
    Result get(String resultId);
}
