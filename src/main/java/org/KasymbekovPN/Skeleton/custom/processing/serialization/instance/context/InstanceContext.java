package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.state.InstanceContextStateMemento;
import org.KasymbekovPN.Skeleton.exception.processing.context.state.ContextStateCareTakerIsEmpty;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface InstanceContext extends Context<InstanceContextStateMemento> {
    Map<String, ObjectNode> getClassNodes();
    Collector getCollector();
    CollectorPath getClassCollectorPath();
    CollectorPath getMembersCollectorPath();
    ClassHeaderPartHandler getClassHeaderPartHandler();
    ClassMembersPartHandler getClassMembersPartHandler();
    void runProcessor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, ContextStateCareTakerIsEmpty, InvocationTargetException;
}
