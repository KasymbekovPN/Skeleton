package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state.Des2InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.functional.OptFunction;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public interface Des2InstanceCxt extends Context<Des2InstanceContextStateMemento> {
    Map<String, ObjectNode> getClassNodes();
    OptFunction<String, Collection<Object>> getCollectionGenerator();
    OptFunction<String, Map<Object,Object>> getMapGenerator();
    OptFunction<String, Object> getInstanceGenerator();
    ClassHeaderPartHandler getClassHeaderPartHandler();
    ClassMembersPartHandler getClassMembersPartHandler();
    CollectorPath getClassPath();
    void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ContextStateCareTakerIsEmpty;
}
