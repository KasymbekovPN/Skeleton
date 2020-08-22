package org.KasymbekovPN.Skeleton.custom.serialization.instance.serializer;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassHeaderHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.KasymbekovPN.Skeleton.lib.serialization.instance.handler.InstanceSerializationHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.instance.serializer.InstanceSerializer;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SkeletonInstanceSerializer implements InstanceSerializer {

    private final static String NOT_MARKED = "Class '%s' isn't marked by according annotation";
    private final static String UNKNOWN_CLASS_NAME = "Unknown class name '%s'";

    private final ClassHeaderHandler classHeaderHandler;
    private final CollectorPath serviceClassPath;
    private final CollectorPath objectPath;
    private final InstanceSerializationHandler instanceSerializationHandler;
    private final String id;
    private final Extractor<String, Annotation[]> annotationClassNameExtractor;

    private Collector collector;
    private Map<String, ObjectNode> classNodes = new HashMap<>();
    private Result result;

    public SkeletonInstanceSerializer(ClassHeaderHandler classHeaderHandler,
                                      CollectorPath serviceClassPath,
                                      CollectorPath objectPath,
                                      InstanceSerializationHandler instanceSerializationHandler,
                                      Collector collector,
                                      String id,
                                      Extractor<String, Annotation[]> annotationClassNameExtractor,
                                      Result result) {
        this.classHeaderHandler = classHeaderHandler;
        this.serviceClassPath = serviceClassPath;
        this.objectPath = objectPath;
        this.instanceSerializationHandler = instanceSerializationHandler;
        this.collector = collector;
        this.id = id;
        this.annotationClassNameExtractor = annotationClassNameExtractor;
        this.result = result;
    }

    @Override
    public Result serialize(Object instance) {

        Triple<Boolean, String, String> checkingResult = checkInstanceClassName(instance);
        Boolean success = checkingResult.getLeft();
        String className = checkingResult.getMiddle();
        String status = checkingResult.getRight();

        instanceSerializationHandler.handle(instance, collector, classNodes.get(className));

        result = result.createNew();
        result.setSuccess(success);
        result.setStatus(status);

        return result;
    }

    @Override
    public Collector getCollector() {
        return collector;
    }

    @Override
    public Collector attachCollector(Collector collector) {
        Collector oldCollector = this.collector;
        this.collector = collector;
        return oldCollector;
    }

    @Override
    public void addClassNode(ObjectNode classNode) {
        Optional<CollectorPath> mayBeClassPath = getClassPath(classNode);
        if (mayBeClassPath.isPresent()){
            CollectorPath classPath = mayBeClassPath.get();
            Optional<Node> mayBeClassNode = classNode.getChild(classPath);
            if (mayBeClassNode.isPresent()){
                ObjectNode classInfoNode = (ObjectNode) mayBeClassNode.get();

                Optional<String> mayBeName = classHeaderHandler.getName(classInfoNode);
                mayBeName.ifPresent(name -> classNodes.put(name, classNode));
            }
        }
    }

    @Override
    public String getId() {
        return id;
    }

    private Triple<Boolean, String, String> checkInstanceClassName(Object instance){
        boolean success = false;
        String status = "";
        String className = "";

        Class<?> instanceClass = instance.getClass();
        Optional<String> mayBeClassName = annotationClassNameExtractor.extract(instanceClass.getDeclaredAnnotations());
        if (mayBeClassName.isPresent()){
            className = mayBeClassName.get();
            if (classNodes.containsKey(className)){
                success = true;
            } else {
                status = String.format(UNKNOWN_CLASS_NAME, className);
            }
        } else {
            status = String.format(NOT_MARKED, instanceClass.getTypeName());
        }

        return new MutableTriple<>(success, className, status);
    }

    private Optional<CollectorPath> getClassPath(ObjectNode classNode){
        Optional<Node> mayBeServiceClassPathNode = classNode.getChild(serviceClassPath);
        if (mayBeServiceClassPathNode.isPresent()){
            ArrayList<String> serviceClassPath = new ArrayList<>();
            ArrayNode serviceClassPathNode = (ArrayNode) mayBeServiceClassPathNode.get();
            for (Node child : serviceClassPathNode.getChildren()) {
                serviceClassPath.add(((StringNode)child).getValue());
            }

            objectPath.setPath(serviceClassPath);
            objectPath.setEi(ObjectNode.ei());

            return Optional.of(objectPath);
        }

        return Optional.empty();
    }
}
