package org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer;

import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.SerializationElementHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SkeletonSerializer implements Serializer {

    private static final Logger log = LoggerFactory.getLogger(SkeletonSerializer.class);

    private final Map<EntityItem, SerializationElementHandler> handlers;

    private Collector collector;

    private SkeletonSerializer(Map<EntityItem, SerializationElementHandler> handlers, Collector collector) {
        this.handlers = handlers;
        this.collector = collector;
    }

    @Override
    public void serialize(Class<?> clazz) {
        handlers.get(SerializerEI.classEI()).handle(clazz, collector);
        for (Field field : clazz.getDeclaredFields()) {
            handlers.get(SerializerEI.memberEI()).handle(field, collector);
        }
    }

    @Override
    public void apply(CollectorProcess collectorProcess) {
        collector.apply(collectorProcess);
    }

    @Override
    public void clear() {
        collector.clear();
    }

    @Override
    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    @Override
    public Collector getCollector() {
        return collector;
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
            return addHandler(SerializerEI.classEI(), seh);
        }

        public Builder addMemberHandler(SerializationElementHandler seh){
            return addHandler(SerializerEI.memberEI(), seh);
        }

        public Serializer build() throws Exception {
            if (collector == null){
                throw new Exception("The collector instance is null");
            }

            if (handlers.size() != SerializerEI.Entity.values().length){
                throw new Exception("Handlers are not setting completely");
            }

            return new SkeletonSerializer(handlers, collector);
        }
    }
}
