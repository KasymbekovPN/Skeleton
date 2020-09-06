package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public interface ClassContext extends Context {
    Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> getAnnotationExtractor();
    List<String> getClassPartPath();
    List<String> getMembersPartPath();
    Class<?> getClazz();
    Collector getCollector();
    boolean checkClassPart();
    Set<Field> getRemainingFields();
    ClassHeaderPartHandler getClassHeaderPartHandler();
    ClassMembersPartHandler getClassMembersPartHandler();
    Class<?> attachClass(Class<?> clazz);
}
