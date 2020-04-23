package org.KasymbekovPN.Skeleton.annotation;

public enum Modifier {
    NONE(0),
    PUBLIC(1),
    PRIVATE(2),
    PROTECTED(4),
    STATIC(8),
    FINAL(16),
    SYNCHRONIZED(32),
    VOLATILE(64),
    TRANSIENT(128),
    NATIVE(256),
    INTERFACE(512),
    ABSTRACT(1_024),
    STRICT(2_048);

    private int modifier;

    public int get() {
        return modifier;
    }

    Modifier(int modifier) {
        this.modifier = modifier;
    }
}
