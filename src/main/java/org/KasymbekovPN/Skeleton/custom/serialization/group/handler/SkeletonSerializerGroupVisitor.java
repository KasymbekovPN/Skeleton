package org.KasymbekovPN.Skeleton.custom.serialization.group.handler;

import org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler.MemberTypeCheckingHandler;
import org.KasymbekovPN.Skeleton.custom.serialization.group.SkeletonSerializerGroup;
import org.KasymbekovPN.Skeleton.lib.collector.handler.CollectorCheckingHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.serialization.group.handler.SerializerGroupVisitor;

import java.util.*;
import java.util.stream.Collectors;

//< need test
public class SkeletonSerializerGroupVisitor implements SerializerGroupVisitor {

    private final static String MEMBER_TYPE_CHECKING = "MEMBER_TYPE_CHECKING";

    private final CollectorCheckingHandler collectorCheckingHandler;
    private final Set<String> systemTypes;

    public SkeletonSerializerGroupVisitor(CollectorCheckingHandler collectorCheckingHandler,
                                          Set<String> systemTypes) {
        this.collectorCheckingHandler = collectorCheckingHandler;
        this.systemTypes = systemTypes;

        this.collectorCheckingHandler.add(MEMBER_TYPE_CHECKING);
    }

    @Override
    public void visit(SkeletonSerializerGroup skeletonSerializerGroup) {
        Map<Class<?>, Node> prepareClasses = skeletonSerializerGroup.getPrepareClasses();
        Set<String> serializedTypes
                = prepareClasses.keySet().stream().map(Class::getTypeName).collect(Collectors.toSet());

        //<
        List<String> path = new ArrayList<>(){{add("members");}};
        //<

        Optional<CollectorCheckingProcess> maybeProcess = collectorCheckingHandler.get(MEMBER_TYPE_CHECKING);
        maybeProcess.ifPresent(process -> {
            new MemberTypeCheckingHandler(
                    process,
                    serializedTypes,
                    systemTypes,
                    ObjectNode.class,
                    path
            );
        });

        for (Map.Entry<Class<?>, Node> entry : prepareClasses.entrySet()) {
            Node node = entry.getValue();
            //<
            System.out.println(collectorCheckingHandler.handle(node));
            //<
        }
    }
}
