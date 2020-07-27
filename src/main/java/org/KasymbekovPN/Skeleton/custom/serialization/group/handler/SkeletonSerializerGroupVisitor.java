package org.KasymbekovPN.Skeleton.custom.serialization.group.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler.MemberTypeCheckingHandler;
import org.KasymbekovPN.Skeleton.custom.format.collector.CollectorStructureEI;
import org.KasymbekovPN.Skeleton.custom.serialization.group.serializer.SkeletonSerializerGroup;
import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.format.collector.CollectorStructure;
import org.KasymbekovPN.Skeleton.lib.format.writing.handler.WritingFormatterHandler;
import org.KasymbekovPN.Skeleton.lib.serialization.group.handler.SerializerGroupVisitor;

import java.util.*;
import java.util.stream.Collectors;

public class SkeletonSerializerGroupVisitor implements SerializerGroupVisitor {

    private final CollectorCheckingHandler collectorCheckingHandler;
    private final CollectorProcess collectorProcess;
    private final WritingFormatterHandler writingFormatterHandler;
    private final Set<String> systemTypes;

    private String data;
    private boolean dataIsValid;

    public SkeletonSerializerGroupVisitor(CollectorCheckingHandler collectorCheckingHandler,
                                          CollectorProcess collectorProcess,
                                          WritingFormatterHandler writingFormatterHandler,
                                          Set<String> systemTypes) {
        this.collectorCheckingHandler = collectorCheckingHandler;
        this.collectorProcess = collectorProcess;
        this.writingFormatterHandler = writingFormatterHandler;
        this.systemTypes = systemTypes;

        this.data = "";
        this.dataIsValid = false;
    }

    public Optional<String> getData() {
        return dataIsValid ? Optional.of(data) : Optional.empty();
    }

    @Override
    public void visit(SkeletonSerializerGroup skeletonSerializerGroup) {
        Map<Class<?>, Node> prepareClasses = skeletonSerializerGroup.getPrepareClasses();
        Map<Class<?>, CollectorStructure> collectorStructureByClasses
                = skeletonSerializerGroup.getCollectorStructureByClasses();
        dataIsValid = false;

        Set<String> knownTypes = getKnownTypes(prepareClasses);
        Map<Class<?>, String> processIdByClass = addCheckingProcesses(knownTypes, collectorStructureByClasses);

        dataIsValid = checkPrepareClasses(prepareClasses, processIdByClass);
        if (dataIsValid) {
            ObjectNode allClassesObjectNode = collectAllPreparedClasses(prepareClasses);
            allClassesObjectNode.apply(collectorProcess);
            data = writingFormatterHandler.getDecoder().getString();
        }
    }

    private Set<String> getKnownTypes(Map<Class<?>, Node> prepareClasses){
        Set<String> knownTypes
                = prepareClasses.keySet().stream().map(Class::getTypeName).collect(Collectors.toSet());
        knownTypes.addAll(systemTypes);

        return knownTypes;
    }

    private Map<Class<?>, String> addCheckingProcesses(Set<String> knownTypes,
                                                       Map<Class<?>, CollectorStructure> collectorStructureByClasses){
        Map<Class<?>, String> processIdByClass = new HashMap<>();
        Set<String> processIds = new HashSet<>();

        for (Map.Entry<Class<?>, CollectorStructure> entry : collectorStructureByClasses.entrySet()) {
            List<String> path = entry.getValue().getPath(CollectorStructureEI.membersEI());
            String processId = String.valueOf(path.hashCode());

            processIdByClass.put(entry.getKey(), processId);

            if (!processIds.contains(processId)){
                processIds.add(processId);

                Optional<CollectorCheckingProcess> mayBeProcess = collectorCheckingHandler.add(processId);
                mayBeProcess.ifPresent(process -> {
                    new MemberTypeCheckingHandler(
                            process,
                            knownTypes,
                            ObjectNode.ei(),
                            path
                    );
                });
            }
        }

        return processIdByClass;
    }

    private boolean checkPrepareClasses(Map<Class<?>, Node> prepareClasses, Map<Class<?>, String> processIdByClass){

        for (Map.Entry<Class<?>, Node> entry : prepareClasses.entrySet()) {
            Class<?> clazz = entry.getKey();
            Node node = entry.getValue();
            String processId = processIdByClass.get(clazz);

            CollectorCheckingResult result = collectorCheckingHandler.handle(processId, node);
            if (!result.equals(CollectorCheckingResult.INCLUDE)){
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
