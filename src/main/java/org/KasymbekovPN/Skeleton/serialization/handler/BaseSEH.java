package org.KasymbekovPN.Skeleton.serialization.handler;

import org.KasymbekovPN.Skeleton.generator.Generator;
import org.KasymbekovPN.Skeleton.utils.ClassCondition;

import java.lang.reflect.Field;

public class BaseSEH implements SerializationElementHandler {

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
    public void handle(Class<?> clazz, Generator generator, ClassCondition condition) {
        if (!runHandlingImplementation(clazz, generator, condition) && next != null){
            next.handle(clazz, generator, condition);
        }
    }

    @Override
    public void handle(Field field, Generator generator, ClassCondition condition) {
        if (!runHandlingImplementation(field, generator, condition) && next != null){
            next.handle(field, generator, condition);
        }
    }

    protected boolean runHandlingImplementation(Class<?> clazz, Generator generator, ClassCondition condition){
        return false;
    }

    protected boolean runHandlingImplementation(Field field, Generator generator, ClassCondition condition){
        return false;
    }
}
