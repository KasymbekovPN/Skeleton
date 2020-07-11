package org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.SerializedDataWrapper;

public interface NodeSerializedDataWrapper extends SerializedDataWrapper {
//    String getData();
    //<
    boolean hasNext();
    Character next();
    void reset();
    //< rename
    void dec();
}
