package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

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
    Object attachInstance(Object instance);
    Object getInstance();
    Set<Triple<Field, Node, ObjectNode>> getMembers(String kind);
    OptionalConverter<Collection<Object>, ObjectNode> getStrType2CollectionConverter();
}
