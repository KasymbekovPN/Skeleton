package org.KasymbekovPN.Skeleton.serialization.serializationElement.header;

import org.KasymbekovPN.Skeleton.serialization.serializationElement.SerializationElement;

/**
 * SE - Serialization Element
 */
public interface HeaderSE extends SerializationElement {
    void setData(Class<?> clazz);
    Class<?> getData();
}
