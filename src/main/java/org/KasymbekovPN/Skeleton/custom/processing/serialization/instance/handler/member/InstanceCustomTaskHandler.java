package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.InstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class InstanceCustomTaskHandler extends BaseContextTaskHandler<InstanceContext> {

    private static final Logger log = LoggerFactory.getLogger(InstanceCustomTaskHandler.class);

    private final String kind;

    private Map<String, Object> values;

    public InstanceCustomTaskHandler(String kind,
                                     SimpleResult simpleResult) {
        super(simpleResult);
        this.kind = kind;
    }

    @Override
    protected void check(InstanceContext context, Task<InstanceContext> task) {
        if (context.isValid()){
            values = context.getValues(kind);
        } else {
            log.error("The context isn't valid");
            simpleResult.setSuccess(false);
        }
    }

    @Override
    protected void doIt(InstanceContext context) {
        InstanceMembersPartHandler instanceMembersPartHandler = context.getInstanceMembersPartHandler();
        CollectorPath membersPartPath = context.getMembersPartPath();
        Collector collector = context.getCollector();

        ObjectNode target = (ObjectNode) collector.setTarget(membersPartPath.getPath());
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            instanceMembersPartHandler.set(target, name, value, context);
        }

        collector.reset();
    }
}
