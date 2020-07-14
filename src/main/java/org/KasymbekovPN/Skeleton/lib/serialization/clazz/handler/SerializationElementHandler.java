package org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;

import java.lang.reflect.Field;

public interface SerializationElementHandler {
    SerializationElementHandler setNext(SerializationElementHandler next);
    void handle(Class<?> clazz, Collector collector);
    void handle(Field field, Collector collector);
}
