package org.KasymbekovPN.Skeleton.serialization.handler;

import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.SerializationElement;

public interface SerializationElementHandler {
    boolean handle(SerializationElement serializationElement, Generator generator);
}
