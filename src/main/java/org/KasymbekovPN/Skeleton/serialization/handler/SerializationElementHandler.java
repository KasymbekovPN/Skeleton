package org.KasymbekovPN.Skeleton.serialization.handler;

import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.serialization.serializationElement.SerializationElement;
import org.KasymbekovPN.Skeleton.utils.GeneralCondition;

public interface SerializationElementHandler {
    boolean handle(SerializationElement serializationElement, Generator generator, GeneralCondition generalCondition);
}
