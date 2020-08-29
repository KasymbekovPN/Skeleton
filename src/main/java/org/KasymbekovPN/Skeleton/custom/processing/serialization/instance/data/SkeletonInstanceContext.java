package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class SkeletonInstanceContext implements InstanceContext {

    private final List<String> taskIds;
    private final List<String> wrapperIds;
    private final Object instance;
    private final Map<String, ObjectNode> classNodes;
    private final Extractor<String, Annotation[]> annotationClassNameExtractor;
    private final Extractor<List<String>, Pair<String, ObjectNode>> memberExtractor;
    private final Collector collector;
    private final CollectorPath serviceClassPath;
    private final CollectorPath serviceMembersPath;
    private final CollectorPath objectPath;
    private final Processor<InstanceContext> processor;

    public SkeletonInstanceContext(List<String> taskIds,
                                   List<String> wrapperIds,
                                   Object instance,
                                   Map<String, ObjectNode> classNodes,
                                   Extractor<String, Annotation[]> annotationClassNameExtractor,
                                   Extractor<List<String>, Pair<String, ObjectNode>> memberExtractor,
                                   Collector collector,
                                   CollectorPath serviceClassPath,
                                   CollectorPath serviceMembersPath,
                                   CollectorPath objectPath,
                                   Processor<InstanceContext> processor
                                ) {
        this.taskIds = taskIds;
        this.wrapperIds = wrapperIds;
        this.instance = instance;
        this.classNodes = classNodes;
        this.annotationClassNameExtractor = annotationClassNameExtractor;
        this.memberExtractor = memberExtractor;
        this.collector = collector;
        this.serviceClassPath = serviceClassPath;
        this.serviceMembersPath = serviceMembersPath;
        this.objectPath = objectPath;
        this.processor = processor;
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

    @Override
    public Optional<ObjectNode> getClassPart(String className) {
        return getPart(className, serviceClassPath);
    }

    @Override
    public Optional<ObjectNode> getMembersPart(String className) {
        return getPart(className, serviceMembersPath);
    }

    @Override
    public Optional<List<String>> getClassPath(String className) {
        Optional<ObjectNode> maybeClassNode = getClassNode(className);
        if (maybeClassNode.isPresent()){
            ObjectNode classNode = maybeClassNode.get();
            return getPath(serviceClassPath, classNode);
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<String>> getMembersPath(String className) {
        Optional<ObjectNode> maybeClassNode = getClassNode(className);
        if (maybeClassNode.isPresent()){
            ObjectNode classNode = maybeClassNode.get();
            return getPath(serviceMembersPath, classNode);
        }

        return Optional.empty();
    }

    private Optional<ObjectNode> getPart(String className, CollectorPath path){
        Optional<ObjectNode> mayBeClassNode = getClassNode(className);
        if (mayBeClassNode.isPresent()){
            ObjectNode classNode = mayBeClassNode.get();
            Optional<List<String>> mayBePath = getPath(serviceMembersPath, classNode);
            if (mayBePath.isPresent()){
                objectPath.setPath(mayBePath.get());
                objectPath.setEi(ObjectNode.ei());

                Optional<Node> mayBePart = classNode.getChild(objectPath);
                if (mayBePart.isPresent()){
                    return Optional.of((ObjectNode) mayBePart.get());
                }
            }
        }

        return Optional.empty();
    }

    private Optional<List<String>> getPath(CollectorPath collectorPath, ObjectNode classNode){
        Optional<Node> mayBePathNode = classNode.getChild(collectorPath);
        if (mayBePathNode.isPresent()){
            ArrayNode pathNode = (ArrayNode) mayBePathNode.get();
            ArrayList<String> path = new ArrayList<>();

            for (Node child : pathNode.getChildren()) {
                path.add(((StringNode) child).getValue());
            }

            if (path.size() > 0){
                return Optional.of(path);
            }
        }

        return Optional.empty();
    }

    @Override
    public InstanceContext createNew(Object instance) {
        return new SkeletonInstanceContext(
                taskIds,
                wrapperIds,
                instance,
                classNodes,
                annotationClassNameExtractor,
                memberExtractor,
                collector,
                serviceClassPath,
                serviceMembersPath,
                objectPath,
                processor
        );
    }

    @Override
    public Processor<InstanceContext> getProcessor() {
        return processor;
    }
}
