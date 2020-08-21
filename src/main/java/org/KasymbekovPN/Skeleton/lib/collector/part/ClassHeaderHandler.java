package org.KasymbekovPN.Skeleton.lib.collector.part;

import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

import java.util.Optional;

public interface ClassHeaderHandler {
    void setType(ObjectNode objectNode, String type);
    void setName(ObjectNode objectNode, String name);
    void setModifiers(ObjectNode objectNode, int modifiers);
    Optional<String> getType(ObjectNode objectNode);
    Optional<String> getName(ObjectNode objectNode);
    Optional<Number> getModifiers(ObjectNode objectNode);
}
