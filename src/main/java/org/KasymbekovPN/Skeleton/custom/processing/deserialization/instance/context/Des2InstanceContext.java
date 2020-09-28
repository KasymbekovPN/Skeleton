package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context;

import org.KasymbekovPN.Skeleton.custom.processing.baseContext.context.Context;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.Set;

public interface Des2InstanceContext extends Context {
    boolean isValid();
    Object attachInstance(Object instance);
    Object getInstance();
    Set<Pair<Field, Node>> getMembers(String kind);
}
