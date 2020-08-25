package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class SkeletonInstanceData implements InstanceData {

    private final List<String> taskIds;
    private final List<String> wrapperIds;
    private final Object instance;
    private final Map<String, ObjectNode> classNodes;
    private final Extractor<String, Annotation[]> annotationClassNameExtractor;
    private final Extractor<List<String>, Pair<String, ObjectNode>> memberExtractor;
    private final Collector collector;

    public SkeletonInstanceData(List<String> taskIds,
                                List<String> wrapperIds,
                                Object instance,
                                Map<String, ObjectNode> classNodes,
                                Extractor<String, Annotation[]> annotationClassNameExtractor,
                                Extractor<List<String>, Pair<String, ObjectNode>> memberExtractor,
                                Collector collector
                                ) {
        this.taskIds = taskIds;
        this.wrapperIds = wrapperIds;
        this.instance = instance;
        this.classNodes = classNodes;
        this.annotationClassNameExtractor = annotationClassNameExtractor;
        this.memberExtractor = memberExtractor;
        this.collector = collector;
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
    public Object getInstance() {
        return instance;
    }

    @Override
    public Optional<String> getClassName() {
        return annotationClassNameExtractor.extract(instance.getClass().getDeclaredAnnotations());
    }

    @Override
    public Optional<ObjectNode> getClassNode(String className) {
        return classNodes.containsKey(className)
                ? Optional.of(classNodes.get(className))
                : Optional.empty();
    }

    @Override
    public Map<String, Field> getFields(String kind) {
        HashMap<String, Field> result = new HashMap<>();
        List<String> members = new ArrayList<>();
        Optional<String> mayBeClassName
                = annotationClassNameExtractor.extract(instance.getClass().getDeclaredAnnotations());
        if (mayBeClassName.isPresent()){
            String className = mayBeClassName.get();
            Optional<ObjectNode> mayBeClassNode = getClassNode(className);
            if (mayBeClassNode.isPresent()){
                ObjectNode classNode = mayBeClassNode.get();
                Optional<List<String>> mayBeMembers = memberExtractor.extract(
                        new MutablePair<>(
                                kind,
                                classNode
                        )
                );
                if (mayBeMembers.isPresent()){
                    members = mayBeMembers.get();
                }
            }
        }

        for (Field declaredField : instance.getClass().getDeclaredFields()) {
            String name = declaredField.getName();
            if (members.contains(name)){
                result.put(name, declaredField);
            }
        }

        return result;
    }

    @Override
    public Collector getCollector() {
        return collector;
    }
}
