package org.KasymbekovPN.Skeleton.lib.serialization.serializer;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.serialization.handler.SerializationElementHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SkeletonSerializer implements Serializer {

    private static final Logger log = LoggerFactory.getLogger(SkeletonSerializer.class);

    private final Map<Entity, SerializationElementHandler> handlers;
    private final Collector collector;

    private SkeletonSerializer(Map<Entity, SerializationElementHandler> handlers, Collector collector) {
        this.handlers = handlers;
        this.collector = collector;
    }

    @Override
    public void serialize(Class<?> clazz) {
        handlers.get(Entity.CLASS).handle(clazz, collector);
        for (Field field : clazz.getDeclaredFields()) {
            handlers.get(Entity.MEMBER).handle(field, collector);
        }
        handlers.get(Entity.CONSTRUCTOR).handle(clazz, collector);
        handlers.get(Entity.METHOD).handle(clazz, collector);
    }

    public enum Entity{
        CLASS,
        MEMBER,
        CONSTRUCTOR,
        METHOD
    }

    static public class Builder{

        private final Collector collector;
        private final Map<Entity, SerializationElementHandler> handlers = new HashMap<>();

        public Builder(Collector collector) {
            this.collector = collector;
        }

        public Builder addHandler(Entity entity, SerializationElementHandler seh){
            if (entity != null && seh != null){
                if (handlers.containsKey(entity)){
                    handlers.get(entity).setNext(seh);
                } else {
                    handlers.put(entity, seh);
                }
            }

            return this;
        }

        public Builder addClassHandler(SerializationElementHandler seh){
            return addHandler(Entity.CLASS, seh);
        }

        public Builder addMemberHandler(SerializationElementHandler seh){
            return addHandler(Entity.MEMBER, seh);
        }

        public Builder addConstructorHandler(SerializationElementHandler seh){
            return addHandler(Entity.CONSTRUCTOR, seh);
        }

        public Builder addMethodHandler(SerializationElementHandler seh){
            return addHandler(Entity.METHOD, seh);
        }

        public Serializer build() throws Exception {
            if (collector == null){
                throw new Exception("The collector instance is null");
            }

            if (handlers.size() != Entity.values().length){
                throw new Exception("Handlers are not setting completely");
            }

            return new SkeletonSerializer(handlers, collector);
        }
    }
}
