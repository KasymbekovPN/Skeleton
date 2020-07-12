package org.KasymbekovPN.Skeleton.custom.deserialization.handler.node.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.handler.node.NodeDeserializerHandler;

import java.util.Optional;

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


// <<<
//    public static class InitialHandler extends BaseHandlerImpl{
//
//        private final static Logger log = LoggerFactory.getLogger(InitialHandler.class);
//
//        private final static Character INNER_OBJECT_HANDLER_TRIGGER = '{';
//
//        public InitialHandler(NodeSerializedDataWrapper dataWrapper, Handler parent, Node parentNode) {
//            super(dataWrapper, parent, parentNode);
//        }
//
//        @Override
//        public Handler run() {
//            while (dataWrapper.hasNext()){
//                Character next = dataWrapper.next();
//                //<
//                log.info("run next : '{}'", next);
//                //<
//                if (next.equals(INNER_OBJECT_HANDLER_TRIGGER)){
//                    //<
//                    log.info("create and return InnerObjectHandler");
//                    //<
//                    return new InnerObjectHandler(dataWrapper, this, null);
//                }
//            }
//
//            return parentHandler;
//        }
//
//        @Override
//        public void setChildNode(Node node) {
//            //<
//            log.info("setChildNode : {}", node);
//            //<
//        }
//    }
//
