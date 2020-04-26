package org.KasymbekovPN.Skeleton.serialization.handler;

import org.KasymbekovPN.Skeleton.condition.AnnotationConditionHandler;
import org.KasymbekovPN.Skeleton.generator.Generator;

import java.lang.reflect.Field;

public interface SerializationElementHandler {
    SerializationElementHandler setNext(SerializationElementHandler next);
    void handle(Class<?> clazz, Generator generator, AnnotationConditionHandler annotationConditionHandler);
    void handle(Field field, Generator generator, AnnotationConditionHandler annotationConditionHandler);
}
