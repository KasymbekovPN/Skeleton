package org.KasymbekovPN.Skeleton.serialization.handler;

import org.KasymbekovPN.Skeleton.collector.Collector;
import org.KasymbekovPN.Skeleton.condition.AnnotationHandler;
import org.KasymbekovPN.Skeleton.condition.SkeletonCheckResult;

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
    public void handle(Class<?> clazz, Collector collector, AnnotationHandler annotationHandler) {
        if (!runHandlingImplementation(clazz, collector, annotationHandler) && next != null){
            next.handle(clazz, collector, annotationHandler);
        }
    }

    @Override
    public void handle(Field field, Collector collector, AnnotationHandler annotationHandler) {
        if (!runHandlingImplementation(field, collector, annotationHandler) && next != null){
            next.handle(field, collector, annotationHandler);
        }
    }

    protected boolean runHandlingImplementation(Class<?> clazz, Collector collector,
                                                AnnotationHandler annotationHandler){
        return false;
    }

    protected boolean runHandlingImplementation(Field field, Collector collector,
                                                AnnotationHandler annotationHandler){
        return false;
    }
}
