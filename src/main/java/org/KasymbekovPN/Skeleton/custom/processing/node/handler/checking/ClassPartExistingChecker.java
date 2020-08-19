package org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking;

import org.KasymbekovPN.Skeleton.lib.collector.path.SkeletonCollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassPartExistingChecker implements TaskHandler<Node> {

    public static final String TASK_NAME = "classExist";

    private static final List<String> CLASS_PATH = new ArrayList<>(){{
        add("__service");
        add("__paths");
        add("CLASS");
    }};

    private Result result;

    public ClassPartExistingChecker(Result result) {
        this.result = result;
    }

    @Override
    public Result handle(Node object, Task<Node> task) {
        boolean success = false;
        String status = "";

        Optional<List<String>> mayBeClassPath = extractClassPath(object);
        if (mayBeClassPath.isPresent()){
            List<String> classPath = mayBeClassPath.get();
//            Optional<Node> mayBeClassNode = object.getChild(classPath, ObjectNode.class);
            //<
            Optional<Node> mayBeClassNode = object.getChild(
                    new SkeletonCollectorPath(classPath, ObjectNode.ei())
            );
            if (mayBeClassNode.isPresent()){
                success = true;
            } else {
                status = "Doesn't contain class node";
            }
        } else {
            status = "Doesn't contain class path";
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

    private Optional<List<String>> extractClassPath(Node object){
//        Optional<Node> mayBeClassPathNode = object.getChild(CLASS_PATH, ArrayNode.class);
        //<
        Optional<Node> mayBeClassPathNode = object.getChild(
                new SkeletonCollectorPath(CLASS_PATH, ArrayNode.ei())
        );
        if (mayBeClassPathNode.isPresent()){
            ArrayList<String> classPath = new ArrayList<>();
            ArrayNode classPathNode = (ArrayNode) mayBeClassPathNode.get();
            for (Node pathPart : classPathNode.getChildren()) {
                classPath.add(((StringNode)pathPart).getValue());
            }

            return Optional.of(classPath);
        }

        return Optional.empty();
    }
}
