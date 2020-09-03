package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.SkeletonCollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class SkeletonClassContext implements ClassContext {

    private final List<String> taskIds;
    private final List<String> wrapperIds;
    private final Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor;
    private final List<String> classPartPath;
    private final List<String> membersPartPath;
    private final Class<?> clazz;
    private final Collector collector;
    private final Set<Field> fields;
    private final ClassHeaderPartHandler classHeaderPartHandler;
    private final ClassMembersPartHandler classMembersPartHandler;

    public SkeletonClassContext(List<String> taskIds,
                                List<String> wrapperIds,
                                Extractor<Annotation, Pair<Class<? extends Annotation>, Annotation[]>> annotationExtractor,
                                List<String> classPartPath,
                                List<String> membersPartPath,
                                Class<?> clazz,
                                Collector collector,
                                ClassHeaderPartHandler classHeaderPartHandler,
                                ClassMembersPartHandler classMembersPartHandler) {
        this.taskIds = taskIds;
        this.wrapperIds = wrapperIds;
        this.annotationExtractor = annotationExtractor;
        this.classPartPath = classPartPath;
        this.membersPartPath = membersPartPath;
        this.clazz = clazz;
        this.collector = collector;
        this.fields = new HashSet<>(Arrays.asList(clazz.getDeclaredFields()));
        this.classHeaderPartHandler = classHeaderPartHandler;
        this.classMembersPartHandler = classMembersPartHandler;
    }

    @Override
    public List<String> getTaskIds() {
        return taskIds;
    }

    @Override
    public List<String> getWrapperIds() {
        return wrapperIds;
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
                new SkeletonCollectorPath(classPartPath, ObjectNode.ei())
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
}
