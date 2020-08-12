package org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking;

import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;

public class NodeTypeChecker implements TaskHandler<Node> {

    private final SimpleChecker<String> systemTypeChecker;

    private Result result;

    public NodeTypeChecker(SimpleChecker<String> systemTypeChecker, Result result) {
        this.result = result;
        this.systemTypeChecker = systemTypeChecker;
    }

    @Override
    public Result handle(Node object, Task<Node> task) {

        System.out.println(object);

        return null;
    }

    @Override
    public Result getHandlerResult() {
        return result;
    }
}
