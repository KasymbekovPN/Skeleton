package org.KasymbekovPN.Skeleton.custom.format.deserialization.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.NodeDeformatter;

import java.util.Optional;

public class JsonNumberNodeDeformatter implements NodeDeformatter {
    @Override
    public void setData(NodeSerializedDataWrapper serializedDataWrapper, Node parent) {

    }

    @Override
    public Optional<Node> getNode() {
        return Optional.empty();
    }
}
