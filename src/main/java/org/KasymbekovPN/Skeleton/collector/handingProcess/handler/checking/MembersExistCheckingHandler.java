package org.KasymbekovPN.Skeleton.collector.handingProcess.handler.checking;

import org.KasymbekovPN.Skeleton.annotation.handler.SkeletonCheckResult;
import org.KasymbekovPN.Skeleton.collector.handingProcess.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.collector.handingProcess.handler.CollectorHandlingProcessHandler;
import org.KasymbekovPN.Skeleton.collector.node.Node;
import org.KasymbekovPN.Skeleton.collector.node.ObjectNode;

import java.util.List;
import java.util.Optional;

//< SKEL-31
public class MembersExistCheckingHandler implements CollectorHandlingProcessHandler {

    private final CollectorCheckingProcess collectorCheckingProcess;
    private final List<String> members;
    private final Class<? extends Node> clazz;
    private final List<String> path;

    public MembersExistCheckingHandler(CollectorCheckingProcess collectorCheckingProcess,
                                       Class<? extends Node> clazz,
                                       List<String> members,
                                       List<String> path) {
        this.clazz = clazz;
        this.collectorCheckingProcess = collectorCheckingProcess;
        this.collectorCheckingProcess.addHandler(clazz, this);
        this.members = members;
        this.path = path;
    }

    @Override
    public void handle(Node node) {

        int counter = -1;
        if (node.isObject()){
            ObjectNode objectNode = (ObjectNode) node;
            Optional<Node> maybeChild = objectNode.getChild(path, ObjectNode.class);
            if (maybeChild.isPresent()){
                ObjectNode membersNode = (ObjectNode) maybeChild.get();
                counter = 0;
                for (String member : members) {
                    if (membersNode.containsKey(member)){
                        counter++;
                    }
                }
            }
        }

        collectorCheckingProcess.setResult(
                clazz,
                members.size() != counter ? SkeletonCheckResult.EXCLUDE : SkeletonCheckResult.INCLUDE
        );
    }
}
