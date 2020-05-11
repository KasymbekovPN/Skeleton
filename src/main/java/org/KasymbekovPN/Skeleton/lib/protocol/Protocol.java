package org.KasymbekovPN.Skeleton.lib.protocol;

public interface Protocol {
    default Long getLibVersion() {return 1L;}
    Long getVersion();
}
