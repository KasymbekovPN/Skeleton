package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.context.state;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.context.state.ContextStateMemento;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public interface Des2InstanceContextStateMemento extends ContextStateMemento {
    Map<String, Set<Triple<Field, Node, ObjectNode>>> getPreparedFieldsData();
}
