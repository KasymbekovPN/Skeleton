package org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;

import java.lang.reflect.Field;

public class BaseSEH implements SerializationElementHandler {

    protected static final CollectorCheckingResult INCLUDE = CollectorCheckingResult.INCLUDE;

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
        if (checkData(clazz, collector)){
            return fillCollector(collector);
        }

        return false;
    }

    protected boolean runHandlingImplementation(Field field, Collector collector){
        if (checkData(field, collector)){
            return fillCollector(collector);
        }

        return false;
    }

    protected boolean checkData(Class<?> clazz, Collector collector){
        return false;
    }

    protected boolean checkData(Field field, Collector collector){
        return false;
    }

    protected boolean fillCollector(Collector collector){
        return false;
    }
}
