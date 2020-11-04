package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.handler.member;

import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.InstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.processing.handler.context.OldBaseContextTaskHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContextOld;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class InstanceCollectionTaskHandlerOld extends OldBaseContextTaskHandler<InstanceContextOld> {

    private static final Logger log = LoggerFactory.getLogger(InstanceCollectionTaskHandlerOld.class);

    private Map<String, Object> values;

    public InstanceCollectionTaskHandlerOld(String id) {
        super(id);
    }

    public InstanceCollectionTaskHandlerOld(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void check(InstanceContextOld context) {
        if (context.isValid()){
            values = context.getValues(id);
        } else {
            log.error("The context isn't valid");
        }
    }

    @Override
    protected void doIt(InstanceContextOld context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
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
