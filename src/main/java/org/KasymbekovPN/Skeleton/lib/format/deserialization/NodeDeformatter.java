package org.KasymbekovPN.Skeleton.lib.format.deserialization;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.SerializedDataWrapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NodeDeformatter {
    void setData(SerializedDataWrapper serializedDataWrapper, Node parent);
    Optional<Node> getNode();
    List<SerializedDataWrapper> getForArray();
    Map<String, SerializedDataWrapper> getForObject();
}
