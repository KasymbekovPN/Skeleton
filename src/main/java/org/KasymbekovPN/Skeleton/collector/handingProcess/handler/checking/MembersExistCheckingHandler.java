package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.checking;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.node.ObjectNode;

import java.util.List;

public class MembersExistCheckingHandler implements CollectorHandlingProcessHandler {

    //< skel-30
    private static final String MEMBERS = "members";

    private final CollectorCheckingProcess collectorCheckingProcess;
    private final List<String> members;
    private final Class<? extends Node> clazz;

    public MembersExistCheckingHandler(CollectorCheckingProcess collectorCheckingProcess,
                                       Class<? extends Node> clazz,
                                       List<String> members) {
        this.clazz = clazz;
        this.collectorCheckingProcess = collectorCheckingProcess;
        this.collectorCheckingProcess.addHandler(clazz, this);
        this.members = members;
    }

    @Override
    public void handle(Node node) {

        int counter = 0;

        if (node.isObject()){
            ObjectNode objectNode = (ObjectNode) node;
            if (objectNode.containsKey(MEMBERS) && objectNode.getChildren().get(MEMBERS).isObject()){
                ObjectNode membersNode = (ObjectNode) objectNode.getChildren().get(MEMBERS);

                for (String member : members) {
                    if (membersNode.containsKey(member)){
                        counter++;
                    }
                }
            }
        }

        collectorCheckingProcess.setResult(
                clazz,
                members.size() == counter ? SkeletonCheckResult.EXCLUDE : SkeletonCheckResult.INCLUDE
        );
    }
}