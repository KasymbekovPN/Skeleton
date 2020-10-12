package org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.handler;

import org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.context.SerClassNodeContext;
import org.KasymbekovPN.Skeleton.lib.checker.SKSimpleChecker;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class SerClassNodeAggregateTaskHandlerOld extends OldBaseContextTaskHandler<SerClassNodeContext> {

    private static final Logger log = LoggerFactory.getLogger(SerClassNodeAggregateTaskHandlerOld.class);

    private Set<String> classNames;

    public SerClassNodeAggregateTaskHandlerOld(String id) {
        super(id);
    }

    public SerClassNodeAggregateTaskHandlerOld(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(SerClassNodeContext context) {

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
        context.setCustomTypeChecker(new SKSimpleChecker<>(classNames));
    }
}
