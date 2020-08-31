package org.KasymbekovPN.Skeleton.custom.serialization.instance.handler;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.KasymbekovPN.Skeleton.lib.serialization.instance.handler.BaseISH;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClassISH extends BaseISH {

    private final static String NOT_MARKED = "Class '%s' isn't marked by according annotation";
    private final static String UNKNOWN_CLASS_NAME = "Unknown class name '%s'";

    private final ClassHeaderPartHandler classHeaderPartHandler;
    private final CollectorPath serviceClassPath;
    private final CollectorPath objectPath;
    private final Extractor<String, Annotation[]> annotationClassNameExtractor;

    private String name;
    private int modifiers;
    private List<String> path;

    public ClassISH(ClassHeaderPartHandler classHeaderPartHandler,
                    CollectorPath serviceClassPath,
                    CollectorPath objectPath,
                    Extractor<String, Annotation[]> annotationClassNameExtractor,
                    Result result) {
        super(result);
        this.classHeaderPartHandler = classHeaderPartHandler;
        this.serviceClassPath = serviceClassPath;
        this.objectPath = objectPath;
        this.annotationClassNameExtractor = annotationClassNameExtractor;
    }

    @Override
    protected boolean checkHeaderData(Object object, String className, Map<String, ObjectNode> classNodes) {
//        Triple<Boolean, String, String> checkingResult = checkInstanceClassName(object, classNodes);
//        Boolean success = checkingResult.getLeft();
        //<
//        String className = checkingResult.getMiddle();
//        String status = checkingResult.getRight();
        //<

        boolean success = false;
        String status = "";

        ObjectNode classNode = classNodes.get(className);
        Optional<CollectorPath> mayBeClassPath = getClassPath(classNode);
        if (mayBeClassPath.isPresent()){
            CollectorPath classPath = mayBeClassPath.get();
            Optional<Node> mayBeClassPart = classNode.getChild(classPath);
            if (mayBeClassPart.isPresent()){
                ObjectNode classPart = (ObjectNode) mayBeClassPart.get();

                Optional<String> mayBeName = classHeaderPartHandler.getName(classPart);
                Optional<Number> mayBeModifiers = classHeaderPartHandler.getModifiers(classPart);
                if (mayBeModifiers.isPresent() && mayBeName.isPresent()){
                    success = true;
                    name = mayBeName.get();
                    modifiers = (int) mayBeModifiers.get();
                    path = classPath.getPath();
                } else {
                    //< !!!
                }
            } else {
                //< !!!
            }
        } else {
            //< !!!
        }

        result = result.createNew();
        result.setSuccess(success);
        result.setStatus(status);

        return success;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        ObjectNode targetNode = (ObjectNode) collector.setTarget(path);
        classHeaderPartHandler.setName(targetNode, name);
        classHeaderPartHandler.setModifiers(targetNode, modifiers);
        collector.reset();

        return false;
    }

    //<
//    private Triple<Boolean, String, String> checkInstanceClassName(Object instance, Map<String, ObjectNode> classNodes){
//        boolean success = false;
//        String status = "";
//        String className = "";
//
//        Class<?> instanceClass = instance.getClass();
//        Optional<String> mayBeClassName = annotationClassNameExtractor.extract(instanceClass.getDeclaredAnnotations());
//        if (mayBeClassName.isPresent()){
//            className = mayBeClassName.get();
//            if (classNodes.containsKey(className)){
//                success = true;
//            } else {
//                status = String.format(UNKNOWN_CLASS_NAME, className);
//            }
//        } else {
//            status = String.format(NOT_MARKED, instanceClass.getTypeName());
//        }
//
//        return new MutableTriple<>(success, className, status);
//    }

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
