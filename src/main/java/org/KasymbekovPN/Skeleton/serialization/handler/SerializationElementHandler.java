package org.KasymbekovPN.Skeleton.serialization.handler;

import org.KasymbekovPN.Skeleton.condition.Condition;
import org.KasymbekovPN.Skeleton.generator.Generator;

import java.lang.reflect.Field;

public interface SerializationElementHandler {
    SerializationElementHandler setNext(SerializationElementHandler next);
    void handle(Class<?> clazz, Generator generator, Condition condition);
    void handle(Field field, Generator generator, Condition condition);
}
