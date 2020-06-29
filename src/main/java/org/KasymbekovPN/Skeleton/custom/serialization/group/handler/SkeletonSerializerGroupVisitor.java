package org.KasymbekovPN.Skeleton.custom.serialization.group.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler.MemberTypeCheckingHandler;
import org.KasymbekovPN.Skeleton.custom.serialization.group.SkeletonSerializerGroup;
import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.writing.CollectorWritingProcess;
import org.KasymbekovPN.Skeleton.lib.serialization.group.handler.SerializerGroupVisitor;

import java.util.*;
import java.util.stream.Collectors;

//< need test
public class SkeletonSerializerGroupVisitor implements SerializerGroupVisitor {

    private final static String MEMBER_TYPE_CHECKING = "MEMBER_TYPE_CHECKING";

    private final CollectorCheckingHandler collectorCheckingHandler;
    private final CollectorWritingProcess collectorWritingProcess;
    private final Set<String> systemTypes;

    private String data;
    private boolean dataIsValid;

    public SkeletonSerializerGroupVisitor(CollectorCheckingHandler collectorCheckingHandler,
                                          CollectorWritingProcess collectorWritingProcess,
                                          Set<String> systemTypes) {
        this.collectorCheckingHandler = collectorCheckingHandler;
        this.collectorWritingProcess = collectorWritingProcess;
        this.systemTypes = systemTypes;

        this.collectorCheckingHandler.add(MEMBER_TYPE_CHECKING);

        this.data = "";
        this.dataIsValid = false;
    }

    public Optional<String> getData() {
        return dataIsValid ? Optional.of(data) : Optional.empty();
    }

    @Override
    public void visit(SkeletonSerializerGroup skeletonSerializerGroup) {
        Map<Class<?>, Node> prepareClasses = skeletonSerializerGroup.getPrepareClasses();
        //<
        List<String> path = new ArrayList<>(){{add("members");}};
        //<
        dataIsValid = false;

        Set<String> knownTypes = getKnownTypes(prepareClasses);
        addProcessHandler(knownTypes, path);
        dataIsValid = checkPrepareClasses(prepareClasses);
        if (dataIsValid){
            ObjectNode allClassesObjectNode = collectAllPreparedClasses(prepareClasses);
            allClassesObjectNode.apply(collectorWritingProcess);
            data = collectorWritingProcess.getBuffer().toString();
        }
    }

    private Set<String> getKnownTypes(Map<Class<?>, Node> prepareClasses){
        Set<String> knownTypes
                = prepareClasses.keySet().stream().map(Class::getTypeName).collect(Collectors.toSet());
        knownTypes.addAll(systemTypes);

        return knownTypes;
    }

    private void addProcessHandler(Set<String> knownTypes, List<String> path){
        Optional<CollectorCheckingProcess> maybeProcess = collectorCheckingHandler.get(MEMBER_TYPE_CHECKING);
        maybeProcess.ifPresent(process -> {
            new MemberTypeCheckingHandler(
                    process,
                    knownTypes,
                    ObjectNode.class,
                    path
            );
        });
    }

    private boolean checkPrepareClasses(Map<Class<?>, Node> prepareClasses){
        for (Map.Entry<Class<?>, Node> entry : prepareClasses.entrySet()) {
            Map<String, CollectorCheckingResult> results = collectorCheckingHandler.handle(entry.getValue());
            if (!results.containsKey(MEMBER_TYPE_CHECKING) ||
                results.get(MEMBER_TYPE_CHECKING) != CollectorCheckingResult.INCLUDE){
                return false;
            }
        }

        return true;
    }

    private ObjectNode collectAllPreparedClasses(Map<Class<?>, Node> preparedClasses){

        ObjectNode allClassesObjectNode = new ObjectNode(null);
        for (Map.Entry<Class<?>, Node> entry : preparedClasses.entrySet()) {
            String typeName = entry.getKey().getTypeName();
            Node node = entry.getValue();
            allClassesObjectNode.addChild(
                    typeName,
                    node.deepCopy(allClassesObjectNode)
            );
        }

        return allClassesObjectNode;
    }
}
