package org.KasymbekovPN.Skeleton.protocol;

public interface Protocol {
    default Long getLibVersion() {return 1L;}
    Long getVersion();
}
