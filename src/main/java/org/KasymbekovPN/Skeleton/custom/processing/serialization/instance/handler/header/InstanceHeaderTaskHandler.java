package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.header;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassHeaderHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.ArrayList;
import java.util.Optional;

public class InstanceHeaderTaskHandler implements TaskHandler<InstanceContext> {

    private static final String CLASS_NAME_IS_NOT_EXIST = "Class Name isn't exist";
    private static final String CLASS_NODE_IS_NOT_EXIST = "Class node '%s' isn't exist";
    private static final String CLASS_PATH_IS_NOT_EXIST = "Class path isn't exist";
    private static final String CLASS_PART_IS_NOT_EXIST = "Class part isn't exist";
    private static final String CLASS_PART_CONTENT_IS_INVALID = "Class part content is invalid";

    private final CollectorPath serviceClassPath;
    private final CollectorPath objectPath;
    private final ClassHeaderHandler classHeaderHandler;

    private Result result;
    private String className;
    private ObjectNode classNode;
    private String name;
    private int modifiers;

    public InstanceHeaderTaskHandler(CollectorPath serviceClassPath,
                                     CollectorPath objectPath,
                                     ClassHeaderHandler classHeaderHandler,
                                     Result result) {
        this.serviceClassPath = serviceClassPath;
        this.objectPath = objectPath;
        this.classHeaderHandler = classHeaderHandler;
        this.result = result;
    }

    @Override
    public Result handle(InstanceContext object, Task<InstanceContext> task) {

        MutablePair<Boolean, String> state = new MutablePair<>(true, "");
        extractClassName(object, state);
        extractClassNode(object, state);
        extractClassPartData(state);

        Boolean success = state.getLeft();
        String status = state.getRight();
        if (success){
            fillCollector(object);
        }

        result = result.createNew();
        result.setSuccess(success);
        result.setStatus(status);

        return result;
    }

    @Override
    public Result getHandlerResult() {
        return result;
    }

    private void extractClassName(InstanceContext instanceContext, MutablePair<Boolean, String> state){
        if (state.getLeft()){
            Optional<String> mayBeClassName = instanceContext.getClassName();
            if (mayBeClassName.isPresent()){
                className = mayBeClassName.get();
            } else {
                state.setLeft(false);
                state.setRight(CLASS_NAME_IS_NOT_EXIST);
            }
        }
    }

    private void extractClassNode(InstanceContext instanceContext, MutablePair<Boolean, String> state){
        if (state.getLeft()){
            Optional<ObjectNode> mayBeClassNode = instanceContext.getClassNode(className);
            if (mayBeClassNode.isPresent()){
                classNode = mayBeClassNode.get();
            } else {
                state.setLeft(false);
                state.setRight(String.format(CLASS_NODE_IS_NOT_EXIST, className));
            }
        }
    }

    private void extractClassPartData(MutablePair<Boolean, String> state){
        if (state.getLeft()){
            Optional<Node> mayBeClassPath = classNode.getChild(serviceClassPath);
            if (mayBeClassPath.isPresent()){
                ArrayList<String> classPath = new ArrayList<>();
                ArrayNode classPathNode = (ArrayNode) mayBeClassPath.get();
                for (Node child : classPathNode.getChildren()) {
                    classPath.add(((StringNode) child).getValue());
                }

                objectPath.setPath(classPath);
                objectPath.setEi(ObjectNode.ei());

                Optional<Node> mayBeClassPartNode = classNode.getChild(objectPath);
                if (mayBeClassPartNode.isPresent()){
                    ObjectNode classPartNode = (ObjectNode) mayBeClassPartNode.get();

                    Optional<String> mayBeName = classHeaderHandler.getName(classPartNode);
                    Optional<Number> mayBeModifiers = classHeaderHandler.getModifiers(classPartNode);
                    if (mayBeName.isPresent() && mayBeModifiers.isPresent()){
                        name = mayBeName.get();
                        modifiers = (int) mayBeModifiers.get();
                    } else {
                        state.setLeft(false);
                        state.setRight(CLASS_PART_CONTENT_IS_INVALID);
                    }
                } else {
                    state.setLeft(false);
                    state.setRight(CLASS_PART_IS_NOT_EXIST);
                }
            } else {
                state.setLeft(false);
                state.setRight(CLASS_PATH_IS_NOT_EXIST);
            }
        }
    }

    private void fillCollector(InstanceContext instanceContext){
        Collector collector = instanceContext.getCollector();
        ObjectNode targetNode = (ObjectNode) collector.setTarget(objectPath.getPath());
        classHeaderHandler.setName(targetNode, name);
        classHeaderHandler.setModifiers(targetNode, modifiers);
        collector.reset();
    }
}
