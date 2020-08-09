package org.KasymbekovPN.Skeleton.custom.serialization.group.serializer;

import org.KasymbekovPN.Skeleton.exception.serialization.group.SerializerGroupBuildException;
import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.result.HandlerResult;
import org.KasymbekovPN.Skeleton.lib.processing.result.TaskResult;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.KasymbekovPN.Skeleton.lib.serialization.group.handler.SerializerGroupVisitor;
import org.KasymbekovPN.Skeleton.lib.serialization.group.serializer.SerializerGroup;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SkeletonSerializerGroup implements SerializerGroup {

    private static final Logger log = LoggerFactory.getLogger(SkeletonSerializerGroup.class);

    public final static String EXTRACT_CLASS_NAME = "extractClassName";

    private final Map<String, Serializer> serializers;
    private final Processor<Node> nodeProcessor;

    private Map<String, Node> prepareClasses = new HashMap<>();
    private Map<String, CollectorStructure> collectorStructureByClasses = new HashMap<>();

    private SkeletonSerializerGroup(Map<String, Serializer> serializers,
                                    Processor<Node> nodeProcessor) {
        this.serializers = serializers;
        this.nodeProcessor = nodeProcessor;
    }

    @Override
    public void handle(String serializerId, Class<?> clazz) {

        Serializer serializer = serializeAndGet(serializerId, clazz);
        Node rootNode = serializer.getCollector().getRoot();
        Triple<Boolean, String, String> extractingResult = extractClassName(rootNode);

        Boolean success = extractingResult.getLeft();
        String className = extractingResult.getMiddle();
        String status = extractingResult.getRight();

        if (success){
            //<
            System.out.println(rootNode);
            //<
            saveDataByClassName(className, rootNode, serializer.getCollector().getCollectorStructure());
        } else {
            log.error("{}", status);
        }
    }

    private Serializer serializeAndGet(String serializerId, Class<?> clazz){
        Serializer serializer = serializers.get(serializerId);
        serializer.serialize(clazz);

        return serializer;
    }

    private Triple<Boolean, String, String> extractClassName(Node node){

        boolean success = false;
        String status = "";
        String className = "";

        Optional<Task<Node>> mayBeTask = nodeProcessor.get(EXTRACT_CLASS_NAME);
        if (mayBeTask.isPresent()){
            Task<Node> nodeTask = mayBeTask.get();
            node.apply(nodeTask);

            TaskResult taskResult = nodeTask.getResult(ObjectNode.ei());
            HandlerResult handlerResult = taskResult.get(ObjectNode.ei());

            success = handlerResult.isSuccess();
            if (success){
                Optional<Object> mayBeClassName = handlerResult.getOptionalData("className");
                if (mayBeClassName.isPresent()){
                    className = (String) mayBeClassName.get();
                } else {
                    status = "Result doesn't contain 'className'";
                }
            } else {
                status = handlerResult.getStatus();
            }
        } else {
            status = "Task '" + EXTRACT_CLASS_NAME + "' doesn't exist";
        }

        return new MutableTriple<>(success, className, status);
    }

    void saveDataByClassName(String className, Node extractedData, CollectorStructure collectorStructure){
        prepareClasses.put(className, extractedData);
        collectorStructureByClasses.put(className, collectorStructure);
    }

    @Override
    public void accept(SerializerGroupVisitor visitor) {
        visitor.visit(this);
    }

    public Map<String, Node> getPrepareClasses() {
        return prepareClasses;
    }

    public Map<String, CollectorStructure> getCollectorStructureByClasses() {
        return collectorStructureByClasses;
    }

    public static class Builder{
        private final Processor<Node> nodeProcessor;
        private final Map<String, Serializer> serializers = new HashMap<>();

        public Builder(Processor<Node> nodeProcessor) {
            this.nodeProcessor = nodeProcessor;
        }

        public Builder addSerializer(String serializerId, Serializer serializer){
            serializers.put(serializerId, serializer);
            return this;
        }

        public SerializerGroup build() throws Exception {
            Optional<Exception> maybeException = check();
            if (maybeException.isPresent()){
                throw maybeException.get();
            } else {
                return new SkeletonSerializerGroup(serializers, nodeProcessor);
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
            if (nodeProcessor == null){
                exceptionMsg += "The Node Processor instance is null; ";
            }

            if (serializers.size() != SerializerGroupEI.Entity.values().length){
                exceptionMsg += "The serializers are not setting completely; ";
            }

            return exceptionMsg;
        }

        private String checkSerializers(){
            StringBuilder exceptionMsg = new StringBuilder();
            for (Map.Entry<String, Serializer> entry : serializers.entrySet()) {
                if (entry.getValue() == null){
                    exceptionMsg.append("The serializer ").append(entry.getKey()).append(" is null; ");
                }
            }

            return exceptionMsg.toString();
        }
    }
}
