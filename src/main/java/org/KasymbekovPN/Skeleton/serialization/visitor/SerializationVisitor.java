package org.KasymbekovPN.Skeleton.serialization.visitor;

import org.KasymbekovPN.Skeleton.serialization.visitor.handler.SerializationVisitorElementHandler;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SerializationVE;

public interface SerializationVisitor {
    boolean visit(SerializationVE ve);
    SerializationVisitor addHandler(Class<? extends SerializationVE> clazz, SerializationVisitorElementHandler handler);
}
