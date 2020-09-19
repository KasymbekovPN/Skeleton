package org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer;

//< del
//import org.KasymbekovPN.Skeleton.exception.serialization.clazz.serializer.skeletonSerializer.SkeletonSerializerBuilderException;
//import org.KasymbekovPN.Skeleton.lib.collector.Collector;
//import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
//import org.KasymbekovPN.Skeleton.lib.serialization.clazz.handler.SerializationElementHandler;
//import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//public class SkeletonSerializer implements Serializer {
//
//    private final Map<EntityItem, SerializationElementHandler> handlers;
//    private final String id;
//
//    private Collector collector;
//
//    private SkeletonSerializer(Map<EntityItem, SerializationElementHandler> handlers,
//                               Collector collector,
//                               String id) {
//        this.handlers = handlers;
//        this.collector = collector;
//        this.id = id;
//    }
//
//    @Override
//    public void serialize(Class<?> clazz) {
//        handlers.get(SerializerEI.classEI()).handle(clazz, collector);
//        for (Field field : clazz.getDeclaredFields()) {
//            handlers.get(SerializerEI.memberEI()).handle(field, collector);
//        }
//    }
//
//    @Override
//    public Collector getCollector() {
//        return collector;
//    }
//
//    @Override
//    public Collector attachCollector(Collector collector) {
//        Collector oldCollector = this.collector;
//        if (collector != null){
//            this.collector = collector;
//        }
//        return oldCollector;
//    }
//
//    @Override
//    public String getId() {
//        return id;
//    }
//
//    static public class Builder{
//
//        private static final Set<String> IDS = new HashSet<>();
//
//        private final String id;
//        private final Collector collector;
//        private final Map<EntityItem, SerializationElementHandler> handlers = new HashMap<>();
//
//        public Builder(Collector collector, String id) {
//            this.collector = collector;
//            this.id = id;
//        }
//
//        public Builder addHandler(EntityItem entityItem, SerializationElementHandler seh){
//            if (entityItem != null && seh != null){
//                if (handlers.containsKey(entityItem)){
//                    handlers.get(entityItem).setNext(seh);
//                } else {
//                    handlers.put(entityItem, seh);
//                }
//            }
//
//            return this;
//        }
//
//        public Builder addClassHandler(SerializationElementHandler seh){
//            return addHandler(SerializerEI.classEI(), seh);
//        }
//
//        public Builder addMemberHandler(SerializationElementHandler seh){
//            return addHandler(SerializerEI.memberEI(), seh);
//        }
//
//        public Serializer build() throws Exception {
//            StringBuilder message = new StringBuilder();
//            checkCollector(message);
//            checkHandlers(message);
//            checkId(message);
//
//            if (message.length() == 0){
//                return new SkeletonSerializer(handlers, collector, id);
//            } else {
//                throw new SkeletonSerializerBuilderException(message.toString());
//            }
//        }
//
//        private void checkCollector(StringBuilder message){
//            if (collector == null){
//                message.append("The collector instance is null; ");
//            }
//        }
//
//        private void checkHandlers(StringBuilder message){
//            if (handlers.size() != SerializerEI.Entity.values().length){
//                message.append("Handlers are not setting completely; ");
//            }
//        }
//
//        private void checkId(StringBuilder message){
//            if (IDS.contains(id)){
//                message.append("Non-unique serializer ID");
//            } else {
//                IDS.add(id);
//            }
//        }
//    }
//}
