package org.KasymbekovPN.Skeleton.custom.serialization.instance.handler;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassHeaderHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.serialization.instance.handler.BaseISH;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassISH extends BaseISH {

    private final ClassHeaderHandler classHeaderHandler;
    private final CollectorPath serviceClassPath;
    private final CollectorPath objectPath;

    private String name;
    private int modifiers;
    private List<String> path;

    public ClassISH(ClassHeaderHandler classHeaderHandler,
                    CollectorPath serviceClassPath,
                    CollectorPath objectPath) {
        this.classHeaderHandler = classHeaderHandler;
        this.serviceClassPath = serviceClassPath;
        this.objectPath = objectPath;
    }

    @Override
    protected boolean checkData(Object object, ObjectNode classNode) {

        boolean result = false;

        Optional<CollectorPath> mayBeClassPath = getClassPath(classNode);
        if (mayBeClassPath.isPresent()){
            CollectorPath classPath = mayBeClassPath.get();
            Optional<Node> mayBeClassPart = classNode.getChild(classPath);
            if (mayBeClassPart.isPresent()){
                ObjectNode classPart = (ObjectNode) mayBeClassPart.get();

                Optional<String> mayBeName = classHeaderHandler.getName(classPart);
                Optional<Number> mayBeModifiers = classHeaderHandler.getModifiers(classPart);
                if (mayBeModifiers.isPresent() && mayBeName.isPresent()){
                    result = true;
                    name = mayBeName.get();
                    modifiers = (int) mayBeModifiers.get();
                    path = classPath.getPath();
                }
            }
        }

        return result;
    }

    @Override
    protected boolean fillCollector(Collector collector) {
        ObjectNode targetNode = (ObjectNode) collector.setTarget(path);
        classHeaderHandler.setName(targetNode, name);
        classHeaderHandler.setModifiers(targetNode, modifiers);
        collector.reset();

        return false;
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
