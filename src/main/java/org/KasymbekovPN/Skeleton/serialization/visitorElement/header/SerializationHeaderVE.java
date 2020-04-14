package org.KasymbekovPN.Skeleton.serialization.visitorElement.header;

import org.KasymbekovPN.Skeleton.serialization.visitor.SerializationVisitor;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SerializationVE;

/**
 * VE - visitor element
 */
public interface SerializationHeaderVE extends SerializationVE {
    void accept(SerializationVisitor visitor);
    void setData(Class clazz);
    Class getData();
}
