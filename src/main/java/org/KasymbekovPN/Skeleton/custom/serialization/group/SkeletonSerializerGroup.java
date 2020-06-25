package org.KasymbekovPN.Skeleton.custom.serialization.group;

import org.KasymbekovPN.Skeleton.custom.collector.process.extraction.handler.RootNodeExtractionHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.group.SerializerGroup;
import org.KasymbekovPN.Skeleton.lib.serialization.group.exceptions.SerializerGroupBuildException;
import org.KasymbekovPN.Skeleton.lib.serialization.group.handler.SerializerGroupHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.serializer.Serializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//< need test
public class SkeletonSerializerGroup implements SerializerGroup {

//    private final Collector collector;
    //<
    private final Map<EntityItem, Serializer> serializers;
    private final CollectorProcess extractionCollectorProcess;
    //<
//    private final CollectorWritingProcess collectorWritingProcess;

    private Map<Class<?>, Node> prepareClasses = new HashMap<>();

    private SkeletonSerializerGroup(Map<EntityItem, Serializer> serializers, CollectorProcess extractionCollectorProcess) {
        this.serializers = serializers;
        this.extractionCollectorProcess = extractionCollectorProcess;
    }
    //<
//    private SkeletonSerializerGroup(Collector collector, Map<EntityItem, Serializer> serializers, CollectorWritingProcess collectorWritingProcess) {
//        this.collector = collector;
//        this.serializers = serializers;
//        this.collectorWritingProcess = collectorWritingProcess;
//    }

    //< may be rename - serializeAndSave
    @Override
    public void handle(EntityItem serializerKey, Class<?> clazz) {
        saveSerializedData(
                clazz,
                serializeAndGet(serializerKey, clazz)
        );
    }

    private Serializer serializeAndGet(EntityItem serializerKey, Class<?> clazz){
        Serializer serializer = serializers.get(serializerKey);
        serializer.serialize(clazz);

        return serializer;
    }

    private void saveSerializedData(Class<?> clazz, Serializer serializer){
        Node root = new ObjectNode(null);
        new RootNodeExtractionHandler(root, extractionCollectorProcess, ObjectNode.class);
        serializer.apply(extractionCollectorProcess);
        prepareClasses.put(clazz, root);
    }

    //< rename method
    @Override
    public void accept(SerializerGroupHandler handler) {
        handler.visit(this);
    }

    public Map<Class<?>, Node> getPrepareClasses() {
        return prepareClasses;
    }

    public static class Builder{

//        private final Collector collector;
        //<
        private final CollectorProcess extractionCollectorProcess;
        private final Map<EntityItem, Serializer> serializers = new HashMap<>();

        public Builder(CollectorProcess extractionCollectorProcess) {
            this.extractionCollectorProcess = extractionCollectorProcess;
        }

        public Builder addSerializer(EntityItem serializerKey, Serializer serializer){
            serializers.put(serializerKey, serializer);

            return this;
        }

        public SerializerGroup build() throws Exception {
            Optional<Exception> maybeException = check();
            if (maybeException.isPresent()){
                throw maybeException.get();
            } else {
                return new SkeletonSerializerGroup(serializers, extractionCollectorProcess);
            }
        }

        private Optional<Exception> check(){
            String exceptionMsg = checkCollectorProcess();
            exceptionMsg += checkSerializers();

            return exceptionMsg.isEmpty()
                    ? Optional.empty()
                    : Optional.of(new SerializerGroupBuildException(exceptionMsg));
        }

        private String checkCollectorProcess(){
            String exceptionMsg = "";
            if (extractionCollectorProcess == null){
                exceptionMsg += "The extraction Collector Process instance is null; ";
            }

            if (serializers.size() != SerializerGroupEI.Entity.values().length){
                exceptionMsg += "The serializers are not setting completely; ";
            }

            return exceptionMsg;
        }

        private String checkSerializers(){
            StringBuilder exceptionMsg = new StringBuilder();
            for (Map.Entry<EntityItem, Serializer> entity : serializers.entrySet()) {
                if (entity.getValue() == null){
                    exceptionMsg.append("The serializer ").append(entity.getKey()).append(" is null; ");
                }
            }

            return exceptionMsg.toString();
        }
    }
}
