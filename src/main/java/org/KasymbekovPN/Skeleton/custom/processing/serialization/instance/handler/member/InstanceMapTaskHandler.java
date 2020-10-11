package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.InstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.BaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class InstanceMapTaskHandler extends BaseContextTaskHandler<InstanceContext> {

    private static final Logger log = LoggerFactory.getLogger(InstanceMapTaskHandler.class);

    private Map<String, Object> values;

    public InstanceMapTaskHandler(String id) {
        super(id);
    }

    public InstanceMapTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(InstanceContext context) {
        if (context.isValid()){
            values = context.getValues(id);
        } else {
            log.error("The context isn't valid");
            simpleResult.setSuccess(false);
        }
    }

    @Override
    protected void doIt(InstanceContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        InstanceMembersPartHandler instanceMembersPartHandler = context.getInstanceMembersPartHandler();
        CollectorPath membersPartPath = context.getMembersPartPath();
        Collector collector = context.getCollector();

        collector.setTarget(membersPartPath.getPath());
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String name = entry.getKey();
            Map<?,?> map = (Map<?, ?>) entry.getValue();

            collector.beginArray(name);

            for (Map.Entry<?, ?> itemEntry : map.entrySet()) {
                Object key = itemEntry.getKey();
                Object value = itemEntry.getValue();

                ObjectNode target = (ObjectNode) collector.beginObject();
                instanceMembersPartHandler.set(target, "key", key, context);
                instanceMembersPartHandler.set(target, "value", value, context);
                collector.end();
            }

            collector.end();
        }
        collector.reset();
    }
}
