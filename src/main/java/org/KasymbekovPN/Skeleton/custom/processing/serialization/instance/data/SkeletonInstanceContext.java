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
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class SkeletonInstanceContext implements InstanceContext {

    private static final String CLASS_NAME_IS_NOT_EXIST = "Class name isn't exist";
    private static final String CLASS_NODE_IS_NOT_EXIST = "Class node isn't exist";
    private static final String PART_IS_NOT_EXIST = "Part isn't exist";
    private static final String PATH_IS_NOT_EXIST = "Path isn't exist";

    private final List<String> taskIds;
    private final List<String> wrapperIds;
    private final Map<String, ObjectNode> classNodes;
    private final Extractor<String, Annotation[]> annotationClassNameExtractor;
    private final Extractor<List<String>, Pair<String, ObjectNode>> memberExtractor;
    private final Collector collector;
    private final CollectorPath serviceClassPath;
    private final CollectorPath serviceMembersPath;
    private final CollectorPath objectPath;
    private final Processor<InstanceContext> processor;

    private Object instance;

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

    @Override
    public Triple<Boolean, String, List<String>> getClassPath1() {

        Triple<Boolean, String, Node> getPartResult = getPart1(serviceClassPath);
        ArrayList<String> path = new ArrayList<>();
        if (getPartResult.getLeft()){
            ArrayNode classPathNode = (ArrayNode) getPartResult.getRight();
            return getPath1(classPathNode);
        }

        return new MutableTriple<>(false, getPartResult.getMiddle(), path);
    }

    @Override
    public Triple<Boolean, String, ObjectNode> getClassPart1() {
        Triple<Boolean, String, List<String>> getClassPathResult = getClassPath1();
        if (getClassPathResult.getLeft()){
            List<String> path = getClassPathResult.getRight();
            objectPath.setEi(ObjectNode.ei());
            objectPath.setPath(path);

            Triple<Boolean, String, Node> getPartResult = getPart1(objectPath);
            return new MutableTriple<>(getPartResult.getLeft(), getPartResult.getMiddle(), (ObjectNode) getPartResult.getRight());
        }

        return new MutableTriple<>(getClassPathResult.getLeft(), getClassPathResult.getMiddle(),new ObjectNode(null));
    }

    public Triple<Boolean, String, Node> getPart1(CollectorPath collectorPath) {

        boolean success = false;
        String status = "";
        Node pathPart = new ObjectNode(null);

        Optional<String> mayBeClassName = getClassName();
        if (mayBeClassName.isPresent()){
            String className = mayBeClassName.get();
            Optional<ObjectNode> maybeClassNode = getClassNode(className);
            if (maybeClassNode.isPresent()){
                ObjectNode classNode = maybeClassNode.get();
                Optional<Node> maybePathPart = classNode.getChild(collectorPath);
                if (maybePathPart.isPresent()){
                    success = true;
                    pathPart = maybePathPart.get();
                } else {
                    status = PART_IS_NOT_EXIST;
                }
            } else {
                status = CLASS_NODE_IS_NOT_EXIST;
            }
        } else {
            status = CLASS_NAME_IS_NOT_EXIST;
        }

        return new MutableTriple<>(success, status, pathPart);
    }

    private Triple<Boolean, String, List<String>> getPath1(ArrayNode node){
        ArrayList<String> path = new ArrayList<>();

        for (Node child : node.getChildren()) {
            path.add(((StringNode) child).getValue());
        }

        boolean success = true;
        String status = "";
        if (path.size()== 0){
            success = false;
            status = PATH_IS_NOT_EXIST;
        }

        return new MutableTriple<>(success, status, path);
    }

    private Optional<ObjectNode> getPart(String className, CollectorPath path){
        Optional<ObjectNode> mayBeClassNode = getClassNode(className);
        if (mayBeClassNode.isPresent()){
            ObjectNode classNode = mayBeClassNode.get();
            Optional<List<String>> mayBePath = getPath(path, classNode);
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
    public Object attachInstance(Object instance) {
        Object oldInstance = this.instance;
        this.instance = instance;
        return oldInstance;
    }

    @Override
    public Processor<InstanceContext> getProcessor() {
        return processor;
    }
}
