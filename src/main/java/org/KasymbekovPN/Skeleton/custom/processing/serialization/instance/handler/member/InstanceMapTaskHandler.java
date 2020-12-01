package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public class InstanceMapTaskHandler extends InstanceBaseTaskHandler {

    public InstanceMapTaskHandler(String id) {
        super(id);
    }

    public InstanceMapTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void doIt(InstanceContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        CollectorPath membersPartPath = context.getMembersCollectorPath();
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
                Optional<Node> maybeKeyNode = convertObjectToNode(key, target, context);
                Optional<Node> maybeValueNode = convertObjectToNode(value, target, context);
                if (maybeKeyNode.isPresent() && maybeValueNode.isPresent()){
                    target.addChild("key", maybeKeyNode.get());
                    target.addChild("value", maybeValueNode.get());
                } else {
                    log.warn("Key and/or value aren't valid : {} : {}", key, value);
                }
                collector.end();
            }

            collector.end();
        }
        collector.reset();
    }
}
