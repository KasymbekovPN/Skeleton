package org.KasymbekovPN.Skeleton.lib.collector.part;

import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

public interface InstanceMembersHandler {
    void setSpecific(ObjectNode objectNode, String property, Object value);
}
