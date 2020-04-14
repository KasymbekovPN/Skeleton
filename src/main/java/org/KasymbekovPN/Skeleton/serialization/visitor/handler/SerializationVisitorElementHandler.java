package org.KasymbekovPN.Skeleton.serialization.visitor.handler;

import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.visitorElement.SerializationVE;

public interface SerializationVisitorElementHandler {
    boolean handle(SerializationVE serializationVE, Generator generator);
}
