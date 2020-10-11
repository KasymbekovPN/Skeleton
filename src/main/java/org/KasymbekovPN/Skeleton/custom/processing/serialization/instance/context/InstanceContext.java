package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart.ClassHeaderPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart.InstanceMembersPartHandler;
import org.KasymbekovPN.Skeleton.lib.processing.context.Context;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;

import java.util.Map;

public interface InstanceContext extends Context {
    ObjectNode getClassNode();
    Collector getCollector();
    Object attachInstance(Object instance);
    Processor<InstanceContext> getProcessor();
    Map<String, Object> getValues(String kind);
    CollectorPath getClassPartPath();
    CollectorPath getMembersPartPath();
    ClassHeaderPartHandler getClassHeaderPartHandler();
    ClassMembersPartHandler getClassMembersPartHandler();
    InstanceMembersPartHandler getInstanceMembersPartHandler();
    boolean isValid();
}
