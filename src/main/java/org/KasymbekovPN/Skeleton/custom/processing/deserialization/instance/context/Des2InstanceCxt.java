package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public interface Des2InstanceCxt extends Context<Des2InstanceContextStateMemento> {
    Map<String, ObjectNode> getClassNodes();
    OptionalConverter<Collection<Object>, String> getCollectionGenerator();
    OptionalConverter<Map<Object, Object>, ObjectNode> getStrType2MapConverter();
    OptionalConverter<Object, String> getClassName2InstanceConverter();
    OptionalConverter<Object, ObjectNode> getToInstanceConverter();
    ClassMembersPartHandler getClassMembersPartHandler();
    void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty;
}
