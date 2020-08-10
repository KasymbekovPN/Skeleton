package org.KasymbekovPN.Skeleton.custom.serialization.group.serializer;

import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.AggregateResult;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.KasymbekovPN.Skeleton.lib.serialization.clazz.serializer.Serializer;
import org.KasymbekovPN.Skeleton.lib.serialization.group.serializer.SerializerGroup;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class SkeletonSerializerGroup implements SerializerGroup {

    private static final Logger log = LoggerFactory.getLogger(SkeletonSerializerGroup.class);

    public final static String EXTRACT_CLASS_NAME = "extractClassName";

    private final Map<String, Serializer> serializers;
    private final Processor<Node> nodeProcessor;

    private Result result;
    private ObjectNode groupNodeRoot = new ObjectNode(null);

    private SkeletonSerializerGroup(Map<String, Serializer> serializers,
                                    Processor<Node> nodeProcessor,
                                    Result result) {
        this.serializers = serializers;
        this.nodeProcessor = nodeProcessor;
        this.result = result;
    }

    @Override
    public Result serialize(String serializerId, Class<?> clazz) {
        Serializer serializer = serializeAndGet(serializerId, clazz);
        Node rootNode = serializer.getCollector().getRoot();
        Triple<Boolean, String, String> extractingResult = extractClassName(rootNode);

        modifyGroupNodeRoot(extractingResult, rootNode);
        return getResult(extractingResult, rootNode);
    }

    @Override
    public void add(String serializerId, Serializer serializer) {
        serializers.put(serializerId, serializer);
    }

    @Override
    public void remove(String serializerId) {
        serializers.remove(serializerId);
    }

    @Override
    public void reset() {
        groupNodeRoot = new ObjectNode(null);
    }

    @Override
    public ObjectNode getGroupRootNode() {
        return groupNodeRoot;
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

            AggregateResult taskResult = nodeTask.getResult(ObjectNode.ei());
            Result result = taskResult.get(ObjectNode.ei().toString());

            success = result.isSuccess();
            if (success){
                Optional<Object> mayBeClassName = result.getOptionalData("className");
                if (mayBeClassName.isPresent()){
                    className = (String) mayBeClassName.get();
                } else {
                    status = "Result doesn't contain 'className'";
                }
            } else {
                status = result.getStatus();
            }
        } else {
            status = "Task '" + EXTRACT_CLASS_NAME + "' doesn't exist";
        }

        return new MutableTriple<>(success, className, status);
    }

    private void modifyGroupNodeRoot(Triple<Boolean, String, String> extractClassNameData, Node rootNode){
        Boolean success = extractClassNameData.getLeft();
        String className = extractClassNameData.getMiddle();
        if (success){
            groupNodeRoot.addChild(className, rootNode);
        }
    }

    private Result getResult(Triple<Boolean, String, String> extractClassNameData, Node rootNode){
        result = result.createNew();
        Boolean success = extractClassNameData.getLeft();
        String status = extractClassNameData.getRight();
        result.setSuccess(success);
        result.setStatus(status);
        result.setOptionalData("node", rootNode);

        return result;
    }
}
