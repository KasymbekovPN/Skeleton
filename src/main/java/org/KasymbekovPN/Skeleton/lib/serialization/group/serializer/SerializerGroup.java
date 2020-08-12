package org.KasymbekovPN.Skeleton.lib.serialization.group.serializer;

import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;

import java.util.Optional;

public interface SerializerGroup {
//    void handle(EntityItem serializerKey, Class<?> clazz);
    //<
//    void handle(String serializerId, Class<?> clazz);
    //<
//    void accept(SerializerGroupVisitor visitor);
    //<
    Result serialize(String serializerId, Class<?> clazz);
    void attach(String serializerId, Serializer serializer);
    Optional<Serializer> detach(String serializerId);
    void reset();
    ObjectNode getGroupRootNode();
}
