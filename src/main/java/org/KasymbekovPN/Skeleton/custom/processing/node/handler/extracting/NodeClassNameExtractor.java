package org.KasymbekovPN.Skeleton.custom.processing.node.handler.extracting;

import org.KasymbekovPN.Skeleton.custom.processing.node.result.handler.NodeClassNameExtractorHandlerResult;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.result.HandlerResult;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NodeClassNameExtractor implements TaskHandler<Node> {

    private static final Logger log = LoggerFactory.getLogger(NodeClassNameExtractor.class);

    private final HandlerResult handlerResult = new NodeClassNameExtractorHandlerResult();
    private final List<String> path;

    public NodeClassNameExtractor(List<String> path) {
        this.path = path;
    }

    @Override
    public HandlerResult handle(Node object, Task<Node> task) {

        ObjectNode objectNode = (ObjectNode) object;
        List<String> classNamePath = new ArrayList<>(path);
        classNamePath.add("name");
        Optional<Node> mayBeClassName = objectNode.getChild(classNamePath, StringNode.class);

        handlerResult.reset();
        if (mayBeClassName.isPresent()) {
            StringNode classNameNode = (StringNode) mayBeClassName.get();
            handlerResult.setSuccess(true);
            handlerResult.setOptionalData("className", classNameNode.getValue());
        } else {
            handlerResult.setSuccess(false);
            handlerResult.setStatus("class name doesn't exist");
        }

        return handlerResult;
    }

    @Override
    public HandlerResult getHandlerResult() {
        return handlerResult;
    }
}
