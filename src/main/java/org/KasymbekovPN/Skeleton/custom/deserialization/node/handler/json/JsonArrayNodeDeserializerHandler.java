package org.KasymbekovPN.Skeleton.custom.deserialization.node.handler.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.handler.NodeDeserializerHandler;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class JsonArrayNodeDeserializerHandler extends JsonBaseNodeDeserializerHandler {

    private static final Set<Character> VALUE_TYPE_DEF_TRIGGERS = new HashSet<>(){{
        add('[');
        add(',');
    }};
    private static final Character THIS_END_TRIGGER = ']';

    public JsonArrayNodeDeserializerHandler(NodeSerializedDataWrapper dataWrapper,
                                            NodeDeserializerHandler parentHandler,
                                            Node parentNode) {
        super(dataWrapper, parentHandler, parentNode);
        node = new ArrayNode(parentNode);
    }

    @Override
    public NodeDeserializerHandler run() {
        State state = State.INIT;

        while (dataWrapper.hasNext()){
            Character next = dataWrapper.next();

            switch (state){
                case INIT:
                    if (VALUE_TYPE_DEF_TRIGGERS.contains(next)){
                        state = State.VALUE_TYPE_DEFINITION;
                    }  else if (next.equals(THIS_END_TRIGGER)){
                        parentHandler.setChildNode(node);
                        return parentHandler;
                    }
                    break;
                case VALUE_TYPE_DEFINITION:
                    Optional<NodeDeserializerHandler> mayBeHandler = createChildHandler(next, node);
                    if (mayBeHandler.isPresent()){
                        return mayBeHandler.get();
                    }
                    break;
            }
        }

        return parentHandler;
    }

    @Override
    public void setChildNode(Node node) {
        this.node.addChild(node);
    }

    private enum State{
        INIT,
        VALUE_TYPE_DEFINITION
    }

    //<
    //    private static class InnerArrayHandler extends BaseHandlerImpl{
//
//        private static final Logger log = LoggerFactory.getLogger(InnerArrayHandler.class);
//
////        private static final Character VALUE_TYPE_DEF_TRIGGER = '[';
//        //<

//
//        private static final Character ARRAY_TRIGGER = '[';
//        private static final Set<Character> BOOLEAN_TRIGGERS = new HashSet<>(){{
//            add('T');
//            add('F');
//            add('t');
//            add('f');
//        }};
//        private static final Character CHARACTER_TRIGGER = '\'';
//        private static final Character OBJECT_TRIGGER = '{';
//        private static final Character STRING_TRIGGER = '"';

//
//        private Node node;
//
//        public InnerArrayHandler(NodeSerializedDataWrapper dataWrapper, Handler parentHandler, Node parentNode) {
//            super(dataWrapper, parentHandler, parentNode);
//            this.node = new ArrayNode(parentNode);
//        }
//
//        @Override
//        public Handler run() {

//        }
//
//        @Override
//        public void setChildNode(Node node) {
//            //<
//            log.info("setChildNode : {}", node);
//            //<
//
//        }
//    }
//

}