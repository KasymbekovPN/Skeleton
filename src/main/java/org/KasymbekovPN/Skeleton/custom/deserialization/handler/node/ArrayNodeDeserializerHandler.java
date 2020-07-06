package org.KasymbekovPN.Skeleton.custom.deserialization.handler.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.SerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.NodeDeformatter;

import java.util.Optional;

public class ArrayNodeDeserializerHandler extends BaseNodeDeserializerHandler {
    public ArrayNodeDeserializerHandler(NodeDeformatter nodeDeformatter) {
        super(nodeDeformatter);
    }

    @Override
    public Optional<Node> handle(SerializedDataWrapper serializedDataWrapper, Node parent) {
        nodeDeformatter.setData(serializedDataWrapper, parent);
        Optional<Node> mayBeNode = nodeDeformatter.getNode();
        if (mayBeNode.isPresent()){
            ObjectNode node = (ObjectNode) mayBeNode.get();
            for (SerializedDataWrapper dataWrapper : nodeDeformatter.getForArray()) {
                Optional<Node> mayBeChild = handlers.get(dataWrapper.getType()).handle(dataWrapper, node);
                mayBeChild.ifPresent(node::addChild);
            }
        }

        return mayBeNode;
    }
}
