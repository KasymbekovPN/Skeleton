package org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

import java.lang.reflect.InvocationTargetException;

public interface InstanceMembersPartHandler {
    void set(ObjectNode objectNode, String property, Object value, InstanceContext instanceContext) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty;
    void set(ArrayNode arrayNode, Object value, InstanceContext instanceContext) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty;
}
