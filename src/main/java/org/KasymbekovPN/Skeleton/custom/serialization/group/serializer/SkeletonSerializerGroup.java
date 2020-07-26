package org.KasymbekovPN.Skeleton.custom.serialization.group.serializer;

import org.KasymbekovPN.Skeleton.custom.collector.process.extraction.handler.RootNodeExtractionHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.serialization.group.serializer.SerializerGroup;
import org.KasymbekovPN.Skeleton.exception.serialization.group.SerializerGroupBuildException;
import org.KasymbekovPN.Skeleton.lib.serialization.group.handler.SerializerGroupVisitor;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SkeletonSerializerGroup implements SerializerGroup {

    private final Map<EntityItem, Serializer> serializers;
    private final CollectorProcess extractionCollectorProcess;

    private Map<Class<?>, Node> prepareClasses = new HashMap<>();
    private Map<Class<?>, CollectorStructure> collectorStructureByClasses = new HashMap<>();

    private SkeletonSerializerGroup(Map<EntityItem, Serializer> serializers, CollectorProcess extractionCollectorProcess) {
        this.serializers = serializers;
        this.extractionCollectorProcess = extractionCollectorProcess;
    }

    @Override
    public void handle(EntityItem serializerKey, Class<?> clazz) {
        Serializer serializer = serializeAndGet(serializerKey, clazz);
        Node extractedData = extractPreparedData(serializer);
        saveDataForClazz(clazz, extractedData, serializer.getCollector().getCollectorStructure());
    }

    private Serializer serializeAndGet(EntityItem serializerKey, Class<?> clazz){
        Serializer serializer = serializers.get(serializerKey);
        serializer.serialize(clazz);

        return serializer;
    }

    private Node extractPreparedData(Serializer serializer){
        Node extractedData = new ObjectNode(null);
        new RootNodeExtractionHandler(extractedData, extractionCollectorProcess, ObjectNode.ei());
        serializer.apply(extractionCollectorProcess);
        serializer.clear();

        return extractedData;
    }

    void saveDataForClazz(Class<?> clazz, Node extractedData, CollectorStructure collectorStructure){
        prepareClasses.put(clazz, extractedData);
        collectorStructureByClasses.put(clazz, collectorStructure);
    }

    @Override
    public void accept(SerializerGroupVisitor visitor) {
        visitor.visit(this);
    }

    public Map<Class<?>, Node> getPrepareClasses() {
        return prepareClasses;
    }

    public Map<Class<?>, CollectorStructure> getCollectorStructureByClasses() {
        return collectorStructureByClasses;
    }

    public static class Builder{

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
