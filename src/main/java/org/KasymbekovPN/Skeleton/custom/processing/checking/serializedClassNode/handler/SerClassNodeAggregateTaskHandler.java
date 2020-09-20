package org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.handler;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.context.SerClassNodeContext;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class SerClassNodeAggregateTaskHandler extends BaseContextTaskHandler {

    private static final Logger log = LoggerFactory.getLogger(SerClassNodeAggregateTaskHandler.class);

    private Set<String> classNames;

    public SerClassNodeAggregateTaskHandler(Result result) {
        super(result);
    }

    @Override
    protected void check(Context context, Task<Context> task) {
        SerClassNodeContext cxt = (SerClassNodeContext) context;

        Map<String, ObjectNode> classNodes = cxt.getClassNodes();
        if (classNodes.size() > 0){
            classNames = classNodes.keySet();
        } else {
//            success = false;
//            status = "Number of nodes equals zero";
//            log.error(status);
            //<
            result.setSuccess(false);
            result.setStatus("Number of nodes equals zero");
            log.error(result.getStatus());
        }
    }

    @Override
    protected void doIt(Context context) {
        SerClassNodeContext cxt = (SerClassNodeContext) context;

        cxt.setCustomTypeChecker(new AllowedStringChecker(classNames));
    }
}
