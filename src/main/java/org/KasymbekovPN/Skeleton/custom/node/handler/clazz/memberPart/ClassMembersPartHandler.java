package org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart;

import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;

import java.util.List;
import java.util.Optional;

public interface ClassMembersPartHandler {
    void setKind(ObjectNode objectNode, String kind);
    void setType(ObjectNode objectNode, String type);
    void setClassName(ObjectNode objectNode, String className);
    void setModifiers(ObjectNode objectNode, int modifiers);
    void setArguments(ObjectNode objectNode, List<String> arguments);
    Optional<String> getKind(ObjectNode objectNode);
    Optional<String> getType(ObjectNode objectNode);
    Optional<String> getClassName(ObjectNode objectNode);
    Optional<Number> getModifiers(ObjectNode objectNode);
    Optional<List<String>> getArguments(ObjectNode objectNode);
}
