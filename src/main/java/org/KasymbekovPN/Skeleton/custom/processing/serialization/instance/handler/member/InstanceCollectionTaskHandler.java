package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.InstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.handler.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public class InstanceCollectionTaskHandler extends BaseContextTaskHandler<InstanceContext> {

    private static final Logger log = LoggerFactory.getLogger(InstanceCollectionTaskHandler.class);

    private final String kind;

    private Map<String, Object> values;

    public InstanceCollectionTaskHandler(String kind, SimpleResult simpleResult) {
        super(simpleResult);
        this.kind = kind;
    }

    @Override
    protected void check(InstanceContext context, Task<InstanceContext> task) {
        if (context.isValid()){
            values = context.getValues(kind);
        } else {
            log.error("The context isn't valid");
        }
    }

    @Override
    protected void doIt(InstanceContext context) {
        CollectorPath membersPartPath = context.getMembersPartPath();
        InstanceMembersPartHandler instanceMembersPartHandler = context.getInstanceMembersPartHandler();
        Collector collector = context.getCollector();

        collector.setTarget(membersPartPath.getPath());
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String name = entry.getKey();
            Collection<?> collection = (Collection<?>) entry.getValue();
            ArrayNode target = (ArrayNode) collector.beginArray(name);
            for (Object value : collection) {
                instanceMembersPartHandler.set(target, value, context);
            }
            collector.end();
        }

        collector.reset();
    }
}
