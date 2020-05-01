package org.KasymbekovPN.Skeleton.serialization.handler;

import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.condition.AnnotationHandler;

import java.lang.reflect.Field;

public interface SerializationElementHandler {
    SerializationElementHandler setNext(SerializationElementHandler next);
    void handle(Class<?> clazz, Collector collector, AnnotationHandler annotationHandler);
    void handle(Field field, Collector collector, AnnotationHandler annotationHandler);
}
