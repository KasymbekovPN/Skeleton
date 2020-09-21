package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.InstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
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

public class InstanceMapTaskHandler extends BaseContextTaskHandler {

    private static final Logger log = LoggerFactory.getLogger(InstanceMapTaskHandler.class);

    private final String kind;

    private Map<String, Object> values;

    public InstanceMapTaskHandler(String kind,
                                  SimpleResult simpleResult) {
        super(simpleResult);
        this.kind = kind;
    }

    @Override
    protected void check(Context context, Task<Context> task) {
        InstanceContext instanceContext = (InstanceContext) context;
        if (instanceContext.isValid()){
            values = instanceContext.getValues(kind);
        } else {
            log.error("The context isn't valid");
//            success = false;
            //<
            simpleResult.setSuccess(false);
        }
    }

    @Override
    protected void doIt(Context context) {
        InstanceContext instanceContext = (InstanceContext) context;

        InstanceMembersPartHandler instanceMembersPartHandler = instanceContext.getInstanceMembersPartHandler();
        CollectorPath membersPartPath = instanceContext.getMembersPartPath();
        Collector collector = instanceContext.getCollector();

        collector.setTarget(membersPartPath.getPath());
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String name = entry.getKey();
            Map<?,?> map = (Map<?, ?>) entry.getValue();

            collector.beginArray(name);

            for (Map.Entry<?, ?> itemEntry : map.entrySet()) {
                Object key = itemEntry.getKey();
                Object value = itemEntry.getValue();

                ObjectNode target = (ObjectNode) collector.beginObject();
                instanceMembersPartHandler.set(target, "key", key, instanceContext);
                instanceMembersPartHandler.set(target, "value", value, instanceContext);
                collector.end();
            }

            collector.end();
        }
        collector.reset();
    }
}
