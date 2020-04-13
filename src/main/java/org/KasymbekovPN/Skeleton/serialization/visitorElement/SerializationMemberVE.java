package org.KasymbekovPN.Skeleton.serialization.visitorElement;

import org.KasymbekovPN.Skeleton.serialization.visitor.SerializationVisitor;

/**
 * VE - visitor element
 */
public interface SerializationMemberVE extends SerializationVE {
    void accept(SerializationVisitor visitor);
}
