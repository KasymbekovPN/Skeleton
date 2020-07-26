package org.KasymbekovPN.Skeleton.lib.format.offset;

public interface Offset {
    void reset();
    void inc();
    void dec();
    String get();
    String prepareTemplate(String template);
}
