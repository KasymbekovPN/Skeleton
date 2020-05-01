package org.KasymbekovPN.Skeleton.condition;

public enum SkeletonCheckResult {
    NONE(0),
    INCLUDE(1),
    EXCLUDE(2);

    private int result;

    SkeletonCheckResult(int result) {
        this.result = result;
    }
}
