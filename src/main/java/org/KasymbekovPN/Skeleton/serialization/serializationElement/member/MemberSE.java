package org.KasymbekovPN.Skeleton.serialization.serializationElement.member;

import org.KasymbekovPN.Skeleton.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.SerializationElement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * SE - serialization element
 *
 * All implementations must contain constructor : <ImplementationName> (SerializationElementHandler handler)
 */
public interface MemberSE extends SerializationElement {
    void setData(Field field);
    Field getData();
    MemberSE setNext(MemberSE next);
    MemberSE setNativeNext(SerializationElementHandler seh) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
