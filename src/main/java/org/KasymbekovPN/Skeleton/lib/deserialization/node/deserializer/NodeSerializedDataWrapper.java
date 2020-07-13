package org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer;

//<
public interface NodeSerializedDataWrapper /*extends SerializedDataWrapper*/ {
    boolean hasNext();
    Character next();
    void reset();
    void decIterator();
}
