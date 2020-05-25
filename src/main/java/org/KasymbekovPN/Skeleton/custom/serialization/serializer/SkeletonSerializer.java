package org.KasymbekovPN.Skeleton.custom.serialization.serializer;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.serializer.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SkeletonSerializer implements Serializer {

    private static final Logger log = LoggerFactory.getLogger(SkeletonSerializer.class);

    private final Map<EntityItem, SerializationElementHandler> handlers;
    private final Collector collector;

    private SkeletonSerializer(Map<EntityItem, SerializationElementHandler> handlers, Collector collector) {
        this.handlers = handlers;
        this.collector = collector;
    }

    @Override
    public void serialize(Class<?> clazz) {
        handlers.get(SkeletonSerializerEI.classEI()).handle(clazz, collector);
        for (Field field : clazz.getDeclaredFields()) {
            handlers.get(SkeletonSerializerEI.memberEI()).handle(field, collector);
        }
        handlers.get(SkeletonSerializerEI.constructorEI()).handle(clazz, collector);
        handlers.get(SkeletonSerializerEI.methodEI()).handle(clazz, collector);
    }

    static public class Builder{

        private final Collector collector;
        private final Map<EntityItem, SerializationElementHandler> handlers = new HashMap<>();

        public Builder(Collector collector) {
            this.collector = collector;
        }

        public Builder addHandler(EntityItem entityItem, SerializationElementHandler seh){
            if (entityItem != null && seh != null){
                if (handlers.containsKey(entityItem)){
                    handlers.get(entityItem).setNext(seh);
                } else {
                    handlers.put(entityItem, seh);
                }
            }

            return this;
        }

        public Builder addClassHandler(SerializationElementHandler seh){
            return addHandler(SkeletonSerializerEI.classEI(), seh);
        }

        public Builder addMemberHandler(SerializationElementHandler seh){
            return addHandler(SkeletonSerializerEI.memberEI(), seh);
        }

        public Builder addConstructorHandler(SerializationElementHandler seh){
            return addHandler(SkeletonSerializerEI.constructorEI(), seh);
        }

        public Builder addMethodHandler(SerializationElementHandler seh){
            return addHandler(SkeletonSerializerEI.methodEI(), seh);
        }

        public Serializer build() throws Exception {
            if (collector == null){
                throw new Exception("The collector instance is null");
            }

            if (handlers.size() != SkeletonSerializerEI.Entity.values().length){
                throw new Exception("Handlers are not setting completely");
            }

            return new SkeletonSerializer(handlers, collector);
        }
    }
}
