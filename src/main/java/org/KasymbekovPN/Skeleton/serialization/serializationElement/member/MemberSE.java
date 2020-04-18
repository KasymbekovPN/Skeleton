package org.KasymbekovPN.Skeleton.serialization.serializationElement.member;

import org.KasymbekovPN.Skeleton.serialization.serializationElement.SerializationElement;

import java.lang.reflect.Field;

/**
 * SE - serialization element
 */
public interface MemberSE extends SerializationElement {
    void setData(Field field);
    Field getData();
    MemberSE setNext(MemberSE next);
}
