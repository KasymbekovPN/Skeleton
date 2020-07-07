package org.KasymbekovPN.Skeleton.custom.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeDeserializer;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeSerializedDataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkeletonNodeDeserializer implements NodeDeserializer {

    static private final Logger log = LoggerFactory.getLogger(SkeletonNodeDeserializer.class);

    private final NodeSerializedDataWrapper dataWrapper;

    public SkeletonNodeDeserializer(NodeSerializedDataWrapper dataWrapper) {
        this.dataWrapper = dataWrapper;
    }

    @Override
    public void deserialize(Collector collector) {

    }

//<
//    private final Map<EntityItem, NodeDeserializerHandler> handlers;
//    private final EntityItem checkingEI;

//    private SkeletonNodeDeserializer(Map<EntityItem, NodeDeserializerHandler> handlers,
//                                     EntityItem checkingEI) {
//        this.handlers = handlers;
//        this.checkingEI = checkingEI;
//    }
//
//    @Override
//    public Node handle(SerializedDataWrapper serializedDataWrapper) {
////        for (Map.Entry<EntityItem, NodeDeserializerHandler> entry : handlers.entrySet()) {
////            NodeDeserializerHandler handler = entry.getValue();
////            Optional<Node> mayBeNode = handler.handle(serializedDataWrapper);
////            if (mayBeNode.isPresent()){
////                return mayBeNode.get();
////            }
////        }
//        //< ??????
//        return null;
//    }

    //<
//    @Override
//    public void addHandler(EntityItem handlerId, NodeDeserializerHandler handler) {
//        if (checkEntityItemInstance(handlerId)){
//            handlers.put(handlerId, handler);
//        }
//    }

    //<
//    private boolean checkEntityItemInstance(EntityItem instance){
//        boolean checkingResult = checkingEI.checkInstance(instance);
//        if (!checkingResult){
//            log.info("{} is invalid ID", instance);
//        }
//        return checkingResult;
//    }
//
//    public static class Builder{
//
//        private final Class<? extends EntityItem> idClazz;
//
//        private Map<EntityItem, NodeDeserializerHandler> handlers = new HashMap<>();
//
//        public Builder(Class<? extends EntityItem> idClazz) {
//            this.idClazz = idClazz;
//        }
//
//        public void addHandler(EntityItem handlerId, NodeDeserializerHandler handler){
//            handlers.put(handlerId, handler);
//        }
//
//        public NodeDeserializer build() throws BuildException {
//            String message = "";
//            Optional<EntityItem> mayBeCheckingEI = createCheckingEI();
//            if (mayBeCheckingEI.isPresent()){
//                Optional<String> mayBeCheckingHandlerIdsMsg = checkHandlerIds(mayBeCheckingEI.get());
//                if (mayBeCheckingHandlerIdsMsg.isPresent()){
//                    message += mayBeCheckingHandlerIdsMsg.get();
//                }
//                Optional<String> mayBeCheckHandlersMsg = checkHandlers();
//                if (mayBeCheckHandlersMsg.isPresent()){
//                    message += mayBeCheckHandlersMsg.get();
//                }
//            } else {
//                message = "Wrong checkingEI; ";
//            }
//
//            if (message.isEmpty()){
//                return new SkeletonNodeDeserializer(handlers, mayBeCheckingEI.get());
//            } else {
//                throw new BuildException(message);
//            }
//        }
//
//        private Optional<EntityItem> createCheckingEI(){
//            try {
//                EntityItem checkingEI = idClazz.getConstructor().newInstance();
//                return Optional.of(checkingEI);
//            } catch (InstantiationException |
//                    InvocationTargetException |
//                    NoSuchMethodException |
//                    IllegalAccessException e) {
//                return Optional.empty();
//            }
//        }
//
//        private Optional<String> checkHandlerIds(EntityItem checkingEI){
//            return checkingEI.checkInstancesStrict(handlers.keySet())
//                    ? Optional.empty()
//                    : Optional.of("No all handlers were installed; ");
//        }
//
//        private Optional<String> checkHandlers(){
//            StringBuilder message = new StringBuilder();
//            for (Map.Entry<EntityItem, NodeDeserializerHandler> entry : handlers.entrySet()) {
//                if (entry.getValue() == null){
//                    message.append("Handler with id '").append(entry.getKey()).append("' is null; ");
//                }
//            }
//
//            return message.length() == 0
//                    ? Optional.empty()
//                    : Optional.of(message.toString());
//        }
//    }
}
