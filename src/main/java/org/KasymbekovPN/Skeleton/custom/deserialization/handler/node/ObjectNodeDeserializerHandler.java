package org.KasymbekovPN.Skeleton.custom.deserialization.handler.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.SerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.format.deserialization.NodeDeformatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class ObjectNodeDeserializerHandler extends BaseNodeDeserializerHandler {

    private static final Logger log = LoggerFactory.getLogger(ObjectNodeDeserializerHandler.class);

    public ObjectNodeDeserializerHandler(NodeDeformatter nodeDeformatter) {
        //< !!! check nodeDeformatter type
        super(nodeDeformatter);
    }

    @Override
    public Optional<Node> handle(SerializedDataWrapper serializedDataWrapper, Node parent) {
        nodeDeformatter.setData(serializedDataWrapper, parent);
        Optional<Node> mayBeNode = nodeDeformatter.getNode();
        if (mayBeNode.isPresent()){
            ObjectNode node = (ObjectNode) mayBeNode.get();
            for (Map.Entry<String, SerializedDataWrapper> entry : nodeDeformatter.getForObject().entrySet()) {
                String property = entry.getKey();
                SerializedDataWrapper data = entry.getValue();
                Optional<Node> mayBeChild = handlers.get(data.getType()).handle(data, node);
                mayBeChild.ifPresent(value -> node.addChild(property, value));
            }
        }

        return mayBeNode;
    }
}
