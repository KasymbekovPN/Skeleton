package org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.SerializedDataWrapper;

public interface NodeSerializedDataWrapper extends SerializedDataWrapper {
    boolean hasNext();
    Character next();
    void reset();
    void decIterator();
}
