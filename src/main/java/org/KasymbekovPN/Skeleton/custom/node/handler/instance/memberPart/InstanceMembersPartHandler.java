package org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart;

import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

public interface InstanceMembersPartHandler {
    void set(ObjectNode objectNode, String property, Object value);
    void set(ArrayNode arrayNode, Object value);
}
