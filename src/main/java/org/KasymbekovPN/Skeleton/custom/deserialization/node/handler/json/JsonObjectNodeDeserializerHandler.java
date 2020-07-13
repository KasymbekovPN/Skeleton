package org.KasymbekovPN.Skeleton.custom.deserialization.node.handler.json;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.deserializer.NodeSerializedDataWrapper;
import org.KasymbekovPN.Skeleton.lib.deserialization.node.handler.NodeDeserializerHandler;

import java.util.Optional;

public class JsonObjectNodeDeserializerHandler extends JsonBaseNodeDeserializerHandler {

    private static final Character NAME_BEGIN_TRIGGER = '"';
    private static final Character NAME_END_TRIGGER = '"';
    private static final Character VALUE_TYPE_DEF_TRIGGER = ':';
    private static final Character THIS_END_TRIGGER = '}';

    private StringBuilder property = new StringBuilder();

    public JsonObjectNodeDeserializerHandler(NodeSerializedDataWrapper dataWrapper,
                                             NodeDeserializerHandler parentHandler,
                                             Node parentNode) {
        super(dataWrapper, parentHandler, parentNode);
        node = new ObjectNode(parentNode);
    }

    @Override
    public NodeDeserializerHandler run() {

        State state = State.INIT;

        while (dataWrapper.hasNext()){
            Character next = dataWrapper.next();

            switch (state){
                case INIT:
                    if (next.equals(NAME_BEGIN_TRIGGER)){
                        property.setLength(0);
                        state = State.NAME_DEFINITION;
                    } else if (next.equals(THIS_END_TRIGGER)){
                        parentHandler.setChildNode(node);
                        return parentHandler;
                    }
                    break;
                case NAME_DEFINITION:
                    if (next.equals(NAME_END_TRIGGER)){
                        state = State.WAIT_SEPARATOR;
                    } else {
                        property.append(next);
                    }
                    break;
                case WAIT_SEPARATOR:
                    if (next.equals(VALUE_TYPE_DEF_TRIGGER)){
                        state = State.VALUE_TYPE_DEFINITION;
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
        this.node.addChild(property.toString(), node);
    }

    private enum State {
        INIT,
        NAME_DEFINITION,
        WAIT_SEPARATOR,
        VALUE_TYPE_DEFINITION
    }

//    public static class InnerObjectHandler extends BaseHandlerImpl{
//
//        private static final Logger log = LoggerFactory.getLogger(InnerObjectHandler.class);
//
//        private static final Character NAME_BEGIN_TRIGGER = '"';
//        private static final Character NAME_END_TRIGGER = '"';
//        private static final Character VALUE_TYPE_DEF_TRIGGER = ':';
//        private static final Character THIS_END_TRIGGER = '}';

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
//        private static final Character THIS_END_TRIGGER = '}';
//
//        //<
//        // 0 - init (after name definition)
//        // 1 - name definition
//        // 2 - wait separator (:)
//        // 3 - value type definition
////        private int state;
//
//        private StringBuilder property = new StringBuilder();
//        private Node node;
//
//        public InnerObjectHandler(NodeSerializedDataWrapper dataWrapper, Handler parentHandler, Node parentNode) {
//            super(dataWrapper, parentHandler, parentNode);
//            node = new ObjectNode(parentNode);
//        }
//
//        @Override
//        public Handler run() {
////            state = 0;
//            //<
//            State state = State.INIT;
//
//            while (dataWrapper.hasNext()){
//                Character next = dataWrapper.next();
//                //<
//                log.info("run next : '{}'", next);
//                //<
//
//                switch (state){
//                    case INIT:
//                        if (next.equals(NAME_BEGIN_TRIGGER)){
//                            property.setLength(0);
//                            state = State.NAME_DEFINITION;
//                        } else if (next.equals(THIS_END_TRIGGER)){
//                            parentHandler.setChildNode(node);
//                            return parentHandler;
//                        }
//                        break;
//                    case NAME_DEFINITION:
//                        if (next.equals(NAME_END_TRIGGER)){
//                            state = State.WAIT_SEPARATOR;
//                        } else {
//                            property.append(next);
//                        }
//                        break;
//                    case WAIT_SEPARATOR:
//                        if (next.equals(VALUE_TYPE_DEF_TRIGGER)){
//                            state = State.VALUE_TYPE_DEFINITION;
//                        }
//                        break;
//                    case VALUE_TYPE_DEFINITION:
//                        if (next.equals(ARRAY_TRIGGER)){
//                            dataWrapper.dec();
//                            return new InnerArrayHandler(dataWrapper, this, node);
//                        } else if (BOOLEAN_TRIGGERS.contains(next)){
//                            dataWrapper.dec();
//                            return new InnerBooleanHandler(dataWrapper, this, node);
//                        } else if (next.equals(CHARACTER_TRIGGER)){
//                            dataWrapper.dec();
//                            return new InnerCharacterHandler(dataWrapper, this, node);
//                        } else if (Character.isDigit(next)){
//                            dataWrapper.dec();
//                            return new InnerNumberHandler(dataWrapper, this, node);
//                        } else if (next.equals(OBJECT_TRIGGER)){
//                            dataWrapper.dec();
//                            return new InnerObjectHandler(dataWrapper, this, node);
//                        } else if (next.equals(STRING_TRIGGER)){
//                            return new InnerStringHandler(dataWrapper, this, node);
//                        }
//                        break;
//                }
//            }
//
//            return parentHandler;
//        }
//
//        @Override
//        public void setChildNode(Node node) {
//            //<
//            log.info("setChildNode : property : {}", property.toString());
//            //<
//
//        }
//    }
//
}
