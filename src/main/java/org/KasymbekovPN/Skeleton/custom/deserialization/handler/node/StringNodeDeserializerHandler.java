package org.KasymbekovPN.Skeleton.custom.deserialization.handler.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.SerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.NodeDeformatter;

import java.util.Optional;

public class StringNodeDeserializerHandler extends BaseNodeDeserializerHandler {
    public StringNodeDeserializerHandler(NodeDeformatter nodeDeformatter) {
        super(nodeDeformatter);
    }

    @Override
    public Optional<Node> handle(SerializedDataWrapper serializedDataWrapper, Node parent) {
        nodeDeformatter.setData(serializedDataWrapper, parent);
        return nodeDeformatter.getNode();
    }
}
