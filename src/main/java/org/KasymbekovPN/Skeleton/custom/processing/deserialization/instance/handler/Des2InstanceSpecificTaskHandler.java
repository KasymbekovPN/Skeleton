package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.handler;

import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.Des2InstanceCxt;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.SimpleResult;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class Des2InstanceSpecificTaskHandler extends Des2InstanceBaseTaskHandler {

    private static final Logger log = LoggerFactory.getLogger(Des2InstanceSpecificTaskHandler.class);

    public Des2InstanceSpecificTaskHandler(String id) {
        super(id);
    }

    public Des2InstanceSpecificTaskHandler(String id, SimpleResult simpleResult) {
        super(id, simpleResult);
    }

    @Override
    protected void doIt(Des2InstanceCxt context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty {
        for (Triple<Field, Node, ObjectNode> member : members) {
            Field field = member.getLeft();
            Node memberNode = member.getMiddle();

            Optional<Object> maybeValue = extractValue(memberNode, context);
            if (maybeValue.isPresent()){
                setField(field, maybeValue.get());
            } else {
                log.warn("{}: Member '{}' has wrong type", id, field.getName());
            }
        }
    }
}
