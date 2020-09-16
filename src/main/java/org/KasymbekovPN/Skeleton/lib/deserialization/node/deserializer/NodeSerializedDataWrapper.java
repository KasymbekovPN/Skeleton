package org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer;

//< !!! del
public interface NodeSerializedDataWrapper /*extends SerializedDataWrapper*/ {
    boolean hasNext();
    Character next();
    void reset();
    void decIterator();
}
