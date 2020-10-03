package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart.ClassMembersPartHandler;
import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.optionalConverter.OptionalConverter;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

public interface Des2InstanceContext extends Context {
    boolean isValid();
    void push(Object instance, ObjectNode serData);
    Object pop();
    Object getInstance();
    Set<Triple<Field, Node, ObjectNode>> getMembers(String kind);
    OptionalConverter<Collection<Object>, ObjectNode> getStrType2CollectionConverter();
    OptionalConverter<Object, String> getClassName2InstanceConverter();
    ClassMembersPartHandler getClassMembersPartHandler();
    void runProcessor();
}
