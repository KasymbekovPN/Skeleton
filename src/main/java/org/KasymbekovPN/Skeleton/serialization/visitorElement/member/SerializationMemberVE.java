package org.KasymbekovPN.Skeleton.serialization.visitorElement.member;

import org.KasymbekovPN.Skeleton.serialization.visitor.SerializationVisitor;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SerializationVE;

import java.lang.reflect.Field;

/**
 * VE - visitor element
 */
public interface SerializationMemberVE extends SerializationVE {
    void accept(SerializationVisitor visitor);
    void setData(Field field);
    Field getData();
    SerializationMemberVE setNext(SerializationMemberVE next);
}
