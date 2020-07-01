package org.KasymbekovPN.Skeleton.custom.deserialization.deserializer.node;

import org.KasymbekovPN.Skeleton.exception.deserialization.deserializer.node.BuildException;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.deserialization.deserializer.node.NodeDeserializer;
import org.KasymbekovPN.Skeleton.lib.deserialization.handler.node.NodeDeserializerHandler;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SkeletonNodeDeserializer implements NodeDeserializer {

    private final Map<EntityItem, NodeDeserializerHandler> handlers;

    private SkeletonNodeDeserializer(Map<EntityItem, NodeDeserializerHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public Node handle(String rawData) {
        return null;
    }

    @Override
    public void addHandler(EntityItem handlerId, NodeDeserializerHandler handler) {

    }

    public static class Builder{

        private final Class<? extends EntityItem> idClazz;

        private Map<EntityItem, NodeDeserializerHandler> handlers = new HashMap<>();

        public Builder(Class<? extends EntityItem> idClazz) {
            this.idClazz = idClazz;
        }

        public void addHandler(EntityItem handlerId, NodeDeserializerHandler handler){
            handlers.put(handlerId, handler);
        }

        public NodeDeserializer build() throws BuildException {
            String message = "";
            Optional<EntityItem> mayBeCheckingEI = createCheckingEI();
            if (mayBeCheckingEI.isPresent()){
                Optional<String> mayBeCheckingHandlerIdsMsg = checkHandlerIds(mayBeCheckingEI.get());
                if (mayBeCheckingHandlerIdsMsg.isPresent()){
                    message += mayBeCheckingHandlerIdsMsg.get();
                }
                Optional<String> mayBeCheckHandlersMsg = checkHandlers();
                if (mayBeCheckHandlersMsg.isPresent()){
                    message += mayBeCheckHandlersMsg.get();
                }
            } else {
                message = "Wrong checkingEI; ";
            }

            if (message.isEmpty()){
                return new SkeletonNodeDeserializer(handlers);
            } else {
                throw new BuildException(message);
            }
        }

        private Optional<EntityItem> createCheckingEI(){
            try {
                EntityItem checkingEI = idClazz.getConstructor().newInstance();
                return Optional.of(checkingEI);
            } catch (InstantiationException |
                    InvocationTargetException |
                    NoSuchMethodException |
                    IllegalAccessException e) {
                return Optional.empty();
            }
        }

        private Optional<String> checkHandlerIds(EntityItem checkingEI){
            return checkingEI.checkInstancesStrict(handlers.keySet())
                    ? Optional.empty()
                    : Optional.of("No all handlers were installed; ");
        }

        private Optional<String> checkHandlers(){
            StringBuilder message = new StringBuilder();
            for (Map.Entry<EntityItem, NodeDeserializerHandler> entry : handlers.entrySet()) {
                if (entry.getValue() == null){
                    message.append("Handler with id '").append(entry.getKey()).append("' is null; ");
                }
            }

            return message.length() == 0
                    ? Optional.empty()
                    : Optional.of(message.toString());
        }
    }
}
