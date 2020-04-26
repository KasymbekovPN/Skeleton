package org.KasymbekovPN.Skeleton.serialization.handler;

import org.KasymbekovPN.Skeleton.condition.AnnotationConditionHandler;
import org.KasymbekovPN.Skeleton.condition.MemberCheckResult;
import org.KasymbekovPN.Skeleton.generator.Generator;

import java.lang.reflect.Field;
import java.util.Set;

public class BaseSEH implements SerializationElementHandler {

    protected static final MemberCheckResult INCLUDE = MemberCheckResult.INCLUDE;

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
    public void handle(Class<?> clazz, Generator generator, AnnotationConditionHandler annotationConditionHandler) {
        if (!runHandlingImplementation(clazz, generator, annotationConditionHandler) && next != null){
            next.handle(clazz, generator, annotationConditionHandler);
        }
    }

    @Override
    public void handle(Field field, Generator generator, AnnotationConditionHandler annotationConditionHandler) {
        if (!runHandlingImplementation(field, generator, annotationConditionHandler) && next != null){
            next.handle(field, generator, annotationConditionHandler);
        }
    }

    protected boolean runHandlingImplementation(Class<?> clazz, Generator generator,
                                                AnnotationConditionHandler annotationConditionHandler){
        return false;
    }

    protected boolean runHandlingImplementation(Field field, Generator generator,
                                                AnnotationConditionHandler annotationConditionHandler){
        return false;
    }

    protected MemberCheckResult resumeCheckResults(Set<MemberCheckResult> results){
        return !results.contains(MemberCheckResult.EXCLUDE)
                && results.contains(MemberCheckResult.INCLUDE)
                ? MemberCheckResult.INCLUDE
                : MemberCheckResult.EXCLUDE;
    }
}
