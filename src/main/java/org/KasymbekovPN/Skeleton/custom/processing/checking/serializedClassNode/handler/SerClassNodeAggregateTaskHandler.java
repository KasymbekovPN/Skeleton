package org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.handler;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.context.SerClassNodeContext;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class SerClassNodeAggregateTaskHandler extends BaseContextTaskHandler<SerClassNodeContext> {

    private static final Logger log = LoggerFactory.getLogger(SerClassNodeAggregateTaskHandler.class);

    private Set<String> classNames;

    public SerClassNodeAggregateTaskHandler() {
    }

    public SerClassNodeAggregateTaskHandler(SimpleResult simpleResult) {
        super(simpleResult);
    }

    @Override
    protected void check(SerClassNodeContext context, Task<SerClassNodeContext> task) {

        Map<String, ObjectNode> classNodes = context.getClassNodes();
        if (classNodes.size() > 0){
            classNames = classNodes.keySet();
        } else {
            simpleResult.setSuccess(false);
            simpleResult.setStatus("Number of nodes equals zero");
            log.error(simpleResult.getStatus());
        }
    }

    @Override
    protected void doIt(SerClassNodeContext context) {
        context.setCustomTypeChecker(new AllowedStringChecker(classNames));
    }
}
