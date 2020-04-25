package org.KasymbekovPN.Skeleton.serialization.handler;

import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.utils.ClassCondition;

import java.lang.reflect.Field;

public interface SerializationElementHandler {
    SerializationElementHandler setNext(SerializationElementHandler next);
    void handle(Class<?> clazz, Generator generator, ClassCondition condition);
    void handle(Field field, Generator generator, ClassCondition condition);
}
