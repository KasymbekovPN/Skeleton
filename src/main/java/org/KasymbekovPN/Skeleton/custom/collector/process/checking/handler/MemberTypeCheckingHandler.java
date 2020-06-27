package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MemberTypeCheckingHandler implements CollectorProcessHandler {

        private final CollectorCheckingProcess collectorCheckingProcess;
        private final Set<String> serializedTypes;
        private final Set<String> systemTypes;
        private final Class<? extends Node> clazz;
        private final List<String> path;

    public MemberTypeCheckingHandler(CollectorCheckingProcess collectorCheckingProcess,
                                     Set<String> serializedTypes,
                                     Set<String> systemTypes,
                                     Class<? extends Node> clazz,
                                     List<String> path) {
        this.collectorCheckingProcess = collectorCheckingProcess;
        this.serializedTypes = serializedTypes;
        this.systemTypes = systemTypes;
        this.clazz = clazz;
        this.path = path;

        this.collectorCheckingProcess.addHandler(this.clazz, this);
    }

    @Override
    public void handle(Node node) {
//            int counter = -1;
        if (node.isObject()){
            ObjectNode objectNode = (ObjectNode) node;
            Optional<Node> maybeChild = objectNode.getChild(path, ObjectNode.class);
            if (maybeChild.isPresent()){
                ObjectNode membersNode = (ObjectNode) maybeChild.get();

                //<
                System.out.println(membersNode);
                //<

//                counter = 0;
//                for (String member : members) {
//                    if (membersNode.containsKey(member)){
//                        counter++;
//                    }
//                }
            }
        }

//        collectorCheckingProcess.setResult(
//                clazz,
//                members.size() != counter ? CollectorCheckingResult.EXCLUDE : CollectorCheckingResult.INCLUDE
//        );
    }
    //<
//        @Override
//        public void handle(Node node) {
//
//            int counter = -1;
//            if (node.isObject()){
//                ObjectNode objectNode = (ObjectNode) node;
//                Optional<Node> maybeChild = objectNode.getChild(path, ObjectNode.class);
//                if (maybeChild.isPresent()){
//                    ObjectNode membersNode = (ObjectNode) maybeChild.get();
//                    counter = 0;
//                    for (String member : members) {
//                        if (membersNode.containsKey(member)){
//                            counter++;
//                        }
//                    }
//                }
//            }
//
//            collectorCheckingProcess.setResult(
//                    clazz,
//                    members.size() != counter ? CollectorCheckingResult.EXCLUDE : CollectorCheckingResult.INCLUDE
//            );
//        }
//    }


}
