package org.KasymbekovPN.Skeleton.condition;

public enum MemberCheckResult {
    NONE(0),
    INCLUDE(1),
    EXCLUDE(2);

    private int result;

    MemberCheckResult(int result) {
        this.result = result;
    }
}
