package org.KasymbekovPN.Skeleton.custom.deserialization.node.handler.json;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.handler.NodeDeserializerHandler;

import java.util.Optional;

//< !!! del
public class JsonNodeDeserializerHandler extends JsonBaseNodeDeserializerHandler {

    private Node node;

    public JsonNodeDeserializerHandler(NodeSerializedDataWrapper dataWrapper,
                                       NodeDeserializerHandler parentHandler,
                                       Node parentNode) {
        super(dataWrapper, parentHandler, parentNode);
    }

    @Override
    public NodeDeserializerHandler run() {

        while (dataWrapper.hasNext()){
            Character next = dataWrapper.next();
            Optional<NodeDeserializerHandler> mayBeHandler = createChildHandler(next, null);
            if (mayBeHandler.isPresent()){
                return mayBeHandler.get();
            }
        }

        return parentHandler;
    }

    @Override
    public void setChildNode(Node node) {
        this.node = node;
    }

    @Override
    public Node getNode() {
        return node;
    }
}
