package org.KasymbekovPN.Skeleton.custom.processing.node.handler.extracting;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeClassNameExtractor implements TaskHandler<Node> {

    private static final Logger log = LoggerFactory.getLogger(NodeClassNameExtractor.class);

    private Result result;

    public NodeClassNameExtractor(Result result) {
        this.result = result;
    }

    @Override
    public Result handle(Node object, Task<Node> task) {


        //<
//        ObjectNode objectNode = (ObjectNode) object;
//        List<String> classNamePath = new ArrayList<>(path);
//        classNamePath.add("name");
//        Optional<Node> mayBeClassName = objectNode.getChild(classNamePath, StringNode.class);
//
//        result = result.createNew();
//        if (mayBeClassName.isPresent()) {
//            StringNode classNameNode = (StringNode) mayBeClassName.get();
//            result.setSuccess(true);
//            result.setOptionalData("className", classNameNode.getValue());
//        } else {
//            result.setSuccess(false);
//            result.setStatus("class name doesn't exist");
//        }

        return result;
    }

    @Override
    public Result getHandlerResult() {
        return result;
    }
}
