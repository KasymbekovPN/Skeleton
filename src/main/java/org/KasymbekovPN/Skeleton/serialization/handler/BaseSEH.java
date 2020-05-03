package org.KasymbekovPN.Skeleton.serialization.handler;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.Collector;

import java.lang.reflect.Field;

public class BaseSEH implements SerializationElementHandler {

    protected static final SkeletonCheckResult INCLUDE = SkeletonCheckResult.INCLUDE;

    private SerializationElementHandler next;

    @Override
    public SerializationElementHandler setNext(SerializationElementHandler next) {
        if (this.next == null){
            this.next = next;
        } else {
            this.next.setNext(next);
        }

        return this;
    }

    @Override
    public void handle(Class<?> clazz, Collector collector) {
        if (!runHandlingImplementation(clazz, collector) && next != null){
            next.handle(clazz, collector);
        }
    }

    @Override
    public void handle(Field field, Collector collector) {
        if (!runHandlingImplementation(field, collector) && next != null){
            next.handle(field, collector);
        }
    }

    protected boolean runHandlingImplementation(Class<?> clazz, Collector collector){
        return false;
    }

    protected boolean runHandlingImplementation(Field field, Collector collector){
        return false;
    }
}
