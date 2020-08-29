package org.KasymbekovPN.Skeleton.lib.collector.part;

import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

public interface InstanceMembersHandler {
    void set(ObjectNode objectNode, String property, Object value);
    void set(ArrayNode arrayNode, Object value);
}
