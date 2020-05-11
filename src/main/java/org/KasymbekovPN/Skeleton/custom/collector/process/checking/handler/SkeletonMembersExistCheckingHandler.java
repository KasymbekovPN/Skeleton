package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.SkeletonObjectNode;

import java.util.List;
import java.util.Optional;

public class SkeletonMembersExistCheckingHandler implements CollectorProcessHandler {

    private final CollectorCheckingProcess collectorCheckingProcess;
    private final List<String> members;
    private final Class<? extends Node> clazz;
    private final List<String> path;

    public SkeletonMembersExistCheckingHandler(CollectorCheckingProcess collectorCheckingProcess,
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
            SkeletonObjectNode skeletonObjectNode = (SkeletonObjectNode) node;
            Optional<Node> maybeChild = skeletonObjectNode.getChild(path, SkeletonObjectNode.class);
            if (maybeChild.isPresent()){
                SkeletonObjectNode membersNode = (SkeletonObjectNode) maybeChild.get();
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
                members.size() != counter ? CollectorCheckingResult.EXCLUDE : CollectorCheckingResult.INCLUDE
        );
    }
}
