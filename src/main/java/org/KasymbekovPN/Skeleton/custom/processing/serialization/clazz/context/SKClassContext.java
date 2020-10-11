package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.ContextIds;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.SKCollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class SKClassContext implements ClassContext {

    private final ContextIds contextIds;
    private final Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor;
    private final List<String> classPartPath;
    private final List<String> membersPartPath;
    private final Collector collector;
    private final ClassHeaderPartHandler classHeaderPartHandler;
    private final ClassMembersPartHandler classMembersPartHandler;

    private Class<?> clazz;
    private Set<Field> fields;

    public SKClassContext(ContextIds contextIds,
                          Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor,
                          List<String> classPartPath,
                          List<String> membersPartPath,
                          Class<?> clazz,
                          Collector collector,
                          ClassHeaderPartHandler classHeaderPartHandler,
                          ClassMembersPartHandler classMembersPartHandler) {
        this.contextIds = contextIds;
        this.annotationExtractor = annotationExtractor;
        this.classPartPath = classPartPath;
        this.membersPartPath = membersPartPath;
        this.collector = collector;
        this.classHeaderPartHandler = classHeaderPartHandler;
        this.classMembersPartHandler = classMembersPartHandler;

        this.clazz = clazz;
        fillFields();
    }

    @Override
    public ContextIds getContextIds() {
        return contextIds;
    }

    @Override
    public Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> getAnnotationExtractor() {
        return annotationExtractor;
    }

    @Override
    public List<String> getClassPartPath() {
        return classPartPath;
    }

    @Override
    public List<String> getMembersPartPath() {
        return membersPartPath;
    }

    @Override
    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public Collector getCollector() {
        return collector;
    }

    @Override
    public boolean checkClassPart() {
        Optional<Node> maybeClassPart = collector.getNode().getChild(
                new SKCollectorPath(classPartPath, ObjectNode.ei())
        );
        return maybeClassPart.isPresent();
    }

    @Override
    public Set<Field> getRemainingFields() {
        return fields;
    }

    @Override
    public ClassHeaderPartHandler getClassHeaderPartHandler() {
        return classHeaderPartHandler;
    }

    @Override
    public ClassMembersPartHandler getClassMembersPartHandler() {
        return classMembersPartHandler;
    }

    @Override
    public Class<?> attachClass(Class<?> clazz) {
        Class<?> oldClazz = this.clazz;
        this.clazz = clazz;
        fillFields();
        return oldClazz;
    }

    private void fillFields(){
        fields = clazz != null
                ? new HashSet<>(Arrays.asList(clazz.getDeclaredFields()))
                : new HashSet<>();
    }
}
