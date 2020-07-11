package org.KasymbekovPN.Skeleton.custom.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.lib.collector.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.BooleanNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeDeserializer;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class SkeletonNodeDeserializer implements NodeDeserializer {

    static private final Logger log = LoggerFactory.getLogger(SkeletonNodeDeserializer.class);

    private Handler handler;

    public SkeletonNodeDeserializer(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void deserialize() {
        do {
            handler = handler.run();
        } while (handler != null);
    }

    public interface Handler{
        Handler run();
        void setChildNode(Node node);
    }

    public static class BaseHandlerImpl implements Handler{

        protected final NodeSerializedDataWrapper dataWrapper;
        protected final Handler parentHandler;
        protected final Node parentNode;

        public BaseHandlerImpl(NodeSerializedDataWrapper dataWrapper, Handler parentHandler, Node parentNode) {
            this.dataWrapper = dataWrapper;
            this.parentHandler = parentHandler;
            this.parentNode = parentNode;
        }

        @Override
        public Handler run() {
            return this;
        }

        @Override
        public void setChildNode(Node node) {}

        protected enum State{
            INIT,
            NAME_DEFINITION,
            WAIT_SEPARATOR,
            VALUE_TYPE_DEFINITION
        }
    }

    public static class InitialHandler extends BaseHandlerImpl{

        private final static Logger log = LoggerFactory.getLogger(InitialHandler.class);

        private final static Character INNER_OBJECT_HANDLER_TRIGGER = '{';

        public InitialHandler(NodeSerializedDataWrapper dataWrapper, Handler parent, Node parentNode) {
            super(dataWrapper, parent, parentNode);
        }

        @Override
        public Handler run() {
            while (dataWrapper.hasNext()){
                Character next = dataWrapper.next();
                //<
                log.info("run next : '{}'", next);
                //<
                if (next.equals(INNER_OBJECT_HANDLER_TRIGGER)){
                    //<
                    log.info("create and return InnerObjectHandler");
                    //<
                    return new InnerObjectHandler(dataWrapper, this, null);
                }
            }

            return parentHandler;
        }

        @Override
        public void setChildNode(Node node) {
            //<
            log.info("setChildNode : {}", node);
            //<
        }
    }

    public static class InnerObjectHandler extends BaseHandlerImpl{

        private static final Logger log = LoggerFactory.getLogger(InnerObjectHandler.class);

        private static final Character NAME_BEGIN_TRIGGER = '"';
        private static final Character NAME_END_TRIGGER = '"';
        private static final Character VALUE_TYPE_DEF_TRIGGER = ':';
        private static final Character ARRAY_TRIGGER = '[';
        private static final Set<Character> BOOLEAN_TRIGGERS = new HashSet<>(){{
            add('T');
            add('F');
            add('t');
            add('f');
        }};
        private static final Character CHARACTER_TRIGGER = '\'';
        private static final Character OBJECT_TRIGGER = '{';
        private static final Character STRING_TRIGGER = '"';
        private static final Character THIS_END_TRIGGER = '}';

        //<
        // 0 - init (after name definition)
        // 1 - name definition
        // 2 - wait separator (:)
        // 3 - value type definition
//        private int state;

        private StringBuilder property = new StringBuilder();
        private Node node;

        public InnerObjectHandler(NodeSerializedDataWrapper dataWrapper, Handler parentHandler, Node parentNode) {
            super(dataWrapper, parentHandler, parentNode);
            node = new ObjectNode(parentNode);
        }

        @Override
        public Handler run() {
//            state = 0;
            //<
            State state = State.INIT;

            while (dataWrapper.hasNext()){
                Character next = dataWrapper.next();
                //<
                log.info("run next : '{}'", next);
                //<

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
                        if (next.equals(ARRAY_TRIGGER)){
                            dataWrapper.dec();
                            return new InnerArrayHandler(dataWrapper, this, node);
                        } else if (BOOLEAN_TRIGGERS.contains(next)){
                            dataWrapper.dec();
                            return new InnerBooleanHandler(dataWrapper, this, node);
                        } else if (next.equals(CHARACTER_TRIGGER)){

                        } else if (Character.isDigit(next)){

                        } else if (next.equals(OBJECT_TRIGGER)){

                        } else if (next.equals(STRING_TRIGGER)){

                        }
                        break;
                }
            }

            return parentHandler;
        }

        @Override
        public void setChildNode(Node node) {
            //<
            log.info("setChildNode : property : {}", property.toString());
            //<
            this.node.addChild(property.toString(), node);
        }
    }

    private static class InnerArrayHandler extends BaseHandlerImpl{

        private static final Logger log = LoggerFactory.getLogger(InnerArrayHandler.class);

//        private static final Character VALUE_TYPE_DEF_TRIGGER = '[';
        //<
        private static final Set<Character> VALUE_TYPE_DEF_TRIGGERS = new HashSet<>(){{
            add('[');
            add(',');
        }};

        private static final Character ARRAY_TRIGGER = '[';
        private static final Set<Character> BOOLEAN_TRIGGERS = new HashSet<>(){{
            add('T');
            add('F');
            add('t');
            add('f');
        }};
        private static final Character CHARACTER_TRIGGER = '\'';
        private static final Character OBJECT_TRIGGER = '{';
        private static final Character STRING_TRIGGER = '"';
        private static final Character THIS_END_TRIGGER = ']';

        private Node node;

        public InnerArrayHandler(NodeSerializedDataWrapper dataWrapper, Handler parentHandler, Node parentNode) {
            super(dataWrapper, parentHandler, parentNode);
            this.node = new ArrayNode(parentNode);
        }

        @Override
        public Handler run() {
            State state = State.INIT;

            while (dataWrapper.hasNext()){
                Character next = dataWrapper.next();

                //<
                log.info("run next : '{}' : {}", next, state);
                //<

                switch (state){
                    case INIT:
//                        if (next.equals(VALUE_TYPE_DEF_TRIGGER)){
                        //<
                        if (VALUE_TYPE_DEF_TRIGGERS.contains(next)){
                            state = State.VALUE_TYPE_DEFINITION;
                        }  else if (next.equals(THIS_END_TRIGGER)){
                            parentHandler.setChildNode(node);
                            return parentHandler;
                        }
                        break;
                    case VALUE_TYPE_DEFINITION:
                        if (next.equals(ARRAY_TRIGGER)){

                        } else if (BOOLEAN_TRIGGERS.contains(next)){
                            dataWrapper.dec();
                            return new InnerBooleanHandler(dataWrapper, this, node);
                        } else if (next.equals(CHARACTER_TRIGGER)){

                        } else if (Character.isDigit(next)){

                        } else if (next.equals(OBJECT_TRIGGER)){

                        } else if (next.equals(STRING_TRIGGER)){

                        }
                        break;
                }
            }

            return parentHandler;
        }

        @Override
        public void setChildNode(Node node) {
            //<
            log.info("setChildNode : {}", node);
            //<
            this.node.addChild(node);
        }
    }

    private static class InnerBooleanHandler extends BaseHandlerImpl{

        private static final Logger log = LoggerFactory.getLogger(InnerBooleanHandler.class);

        private static final Set<Character> VALUE_END_TRIGGERS = new HashSet<>(){{
            add(',');
            add('}');
            add(']');
        }};

        public InnerBooleanHandler(NodeSerializedDataWrapper dataWrapper, Handler parentHandler, Node parentNode) {
            super(dataWrapper, parentHandler, parentNode);
        }

        @Override
        public Handler run() {
            String rawValue = getRawValue();
            BooleanNode booleanNode = new BooleanNode(parentNode, Boolean.valueOf(rawValue));
            parentHandler.setChildNode(booleanNode);
            return parentHandler;
        }

        private String getRawValue(){
            StringBuilder rawValue = new StringBuilder();
            while (dataWrapper.hasNext()){
                Character next = dataWrapper.next();

                //<
                log.info("run next : '{}'", next);
                //<
                if (VALUE_END_TRIGGERS.contains(next)){
                    dataWrapper.dec();
                    break;
                }

                rawValue.append(next);
            }
            //<
            log.info("getRawValue::return {}", rawValue.toString());
            //<
            return rawValue.toString();
        }
    }
}
