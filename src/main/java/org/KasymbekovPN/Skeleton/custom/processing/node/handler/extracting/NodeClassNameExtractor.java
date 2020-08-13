package org.KasymbekovPN.Skeleton.custom.processing.node.handler.extracting;

import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NodeClassNameExtractor implements TaskHandler<Node> {

    //< !!! replace strings with constants !!!

    private static final Logger log = LoggerFactory.getLogger(NodeClassNameExtractor.class);
    private static final List<String> path = new ArrayList<>(){{
        add("__service");
        add("__paths");
        add("CLASS");
    }};

    private Result result;

    public NodeClassNameExtractor(Result result) {
        this.result = result;
    }

    @Override
    public Result handle(Node object, Task<Node> task) {
        ObjectNode objectNode = (ObjectNode) object;
        Optional<List<String>> mayBeClassPath = extractClassPath(objectNode);
        if (mayBeClassPath.isPresent()){
            List<String> classPath = mayBeClassPath.get();
            classPath.add("name");
            Optional<Node> mayBeClassName = objectNode.getChild(classPath, StringNode.class);

            result = result.createNew();
            if (mayBeClassName.isPresent()) {
                StringNode classNameNode = (StringNode) mayBeClassName.get();
                result.setSuccess(true);
                result.setOptionalData("className", classNameNode.getValue());
            } else {
                result.setSuccess(false);
                result.setStatus("class name doesn't exist");
            }
        } else {
            result.setSuccess(false);
            result.setStatus("__service doesn't contain paths");
        }

        return result;
    }

    @Override
    public Result getHandlerResult() {
        return result;
    }

    private Optional<List<String>> extractClassPath(ObjectNode node){

        Optional<Node> mayBeClass = node.getChild(path, ArrayNode.class);
        if (mayBeClass.isPresent()){
            ArrayList<String> classPath = new ArrayList<>();
            ArrayNode classPathNode = (ArrayNode) mayBeClass.get();
            for (Node child : classPathNode.getChildren()) {
                classPath.add(
                        ((StringNode)child).getValue()
                );
            }

            return Optional.of(classPath);
        }

        return Optional.empty();
    }
}
