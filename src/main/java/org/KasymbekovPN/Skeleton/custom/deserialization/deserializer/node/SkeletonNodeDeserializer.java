package org.KasymbekovPN.Skeleton.custom.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
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
        void setNode(Node node);
    }

    public static class BaseHandlerImpl implements Handler{

        protected final NodeSerializedDataWrapper dataWrapper;
        protected final Handler parent;
        protected final Collector collector;

        public BaseHandlerImpl(NodeSerializedDataWrapper dataWrapper, Handler parent, Collector collector) {
            this.dataWrapper = dataWrapper;
            this.parent = parent;
            this.collector = collector;
        }

        @Override
        public Handler run() {
            return this;
        }

        @Override
        public void setNode(Node node) {
        }
    }

    public static class InitialHandler extends BaseHandlerImpl{

        private final static Logger log = LoggerFactory.getLogger(InitialHandler.class);

        private final static Character INNER_OBJECT_HANDLER_TRIGGER = '{';

        public InitialHandler(NodeSerializedDataWrapper dataWrapper, Handler parent, Collector collector) {
            super(dataWrapper, parent, collector);
        }

        @Override
        public Handler run() {
            while (dataWrapper.hasNext()){
                Character next = dataWrapper.next();
                //<
                log.info("run next : {}", next);
                //<
                if (next.equals(INNER_OBJECT_HANDLER_TRIGGER)){
                    //<
                    log.info("create and return InnerObjectHandler");
                    //<
                    return new InnerObjectHandler(dataWrapper, this, collector);
                }
            }

            return parent;
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
        private int state;

        private StringBuilder property = new StringBuilder();

        public InnerObjectHandler(NodeSerializedDataWrapper dataWrapper, Handler parent, Collector collector) {
            super(dataWrapper, parent, collector);
        }

        @Override
        public Handler run() {
            state = 0;

            while (dataWrapper.hasNext()){
                Character next = dataWrapper.next();
                //<
                log.info("run next : {}", next);
                //<

                switch (state){
                    case 0:
                        if (next.equals(NAME_BEGIN_TRIGGER)){
                            property.setLength(0);
                            state = 1;
                        } else if (next.equals(THIS_END_TRIGGER)){
                            //< !!! set this Node into parent instance
                            return parent;
                        }
                        break;
                    case 1:
                        if (next.equals(NAME_END_TRIGGER)){
                            state = 2;
                        } else {
                            property.append(next);
                        }
                        break;
                    case 2:
                        if (next.equals(VALUE_TYPE_DEF_TRIGGER)){
                            state = 3;
                        }
                        break;
                    case 3:
                        if (next.equals(ARRAY_TRIGGER)){

                        } else if (BOOLEAN_TRIGGERS.contains(next)){

                        } else if (next.equals(CHARACTER_TRIGGER)){

                        } else if (Character.isDigit(next)){

                        } else if (next.equals(OBJECT_TRIGGER)){

                        } else if (next.equals(STRING_TRIGGER)){

                        }
                        break;
                }
            }

            return parent;
        }

        @Override
        public void setNode(Node node) {
            //< !!!!!
        }
    }
}
