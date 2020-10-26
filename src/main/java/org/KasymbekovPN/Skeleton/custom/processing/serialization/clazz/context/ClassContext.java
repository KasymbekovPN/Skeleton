package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.context.state.ClassContextStateMemento;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;

public interface ClassContext extends Context<ClassContextStateMemento> {
    CollectorPath getClassPartPath();
    CollectorPath getMembersPartPath();
    Collector getCollector();
    ClassHeaderPartHandler getClassHeaderPartHandler();
    ClassMembersPartHandler getClassMembersPartHandler();
}
