package org.KasymbekovPN.Skeleton.custom.collector.process.checking.handler;

import org.KasymbekovPN.Skeleton.lib.collector.CollectorCheckingResult;
import org.KasymbekovPN.Skeleton.lib.collector.node.Node;
import org.KasymbekovPN.Skeleton.lib.collector.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.collector.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.collector.process.CollectorProcessHandler;
import org.KasymbekovPN.Skeleton.lib.collector.process.checking.CollectorCheckingProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MemberTypeCheckingHandler implements CollectorProcessHandler {

    static private final Logger log = LoggerFactory.getLogger(MemberTypeCheckingHandler.class);

    static private final String TYPE_PROPERTY = "type";

    private final CollectorCheckingProcess collectorCheckingProcess;
    private final Set<String> knownTypes;
    private final Class<? extends Node> clazz;
    private final List<String> path;

    public MemberTypeCheckingHandler(CollectorCheckingProcess collectorCheckingProcess,
                                     Set<String> serializedTypes,
                                     Set<String> systemTypes,
                                     Class<? extends Node> clazz,
                                     List<String> path) {
        this.collectorCheckingProcess = collectorCheckingProcess;
        this.knownTypes = new HashSet<>(serializedTypes);
        this.knownTypes.addAll(systemTypes);
        this.clazz = clazz;
        this.path = path;

        //<
//        this.collectorCheckingProcess.addHandler(this.clazz, this);
    }

    public MemberTypeCheckingHandler(CollectorCheckingProcess collectorCheckingProcess,
                                     Set<String> knownTypes,
                                     Class<? extends Node> clazz,
                                     List<String> path) {
        this.collectorCheckingProcess = collectorCheckingProcess;
        this.knownTypes = knownTypes;
        this.clazz = clazz;
        this.path = path;

        //<
//        this.collectorCheckingProcess.addHandler(this.clazz, this);
    }

    @Override
    public void handle(Node node) {
        CollectorCheckingResult result = CollectorCheckingResult.NONE;

        Optional<ObjectNode> mayBeMembersNode = getMembersNode(node, path);
        if (mayBeMembersNode.isPresent()){
            Set<String> memberTypes = getMemberTypes(mayBeMembersNode.get());
            result = checkMemberTypes(memberTypes, knownTypes);
        }

        collectorCheckingProcess.setResult(clazz, result);
    }

    private Optional<ObjectNode> getMembersNode(Node root, List<String> path){
        if (root.isObject()){
            Optional<Node> mayBeMembersNode = ((ObjectNode) root).getChild(path, ObjectNode.class);
            if (mayBeMembersNode.isPresent() && mayBeMembersNode.get().isObject()){
                return Optional.of((ObjectNode) mayBeMembersNode.get());
            }
        }

        return Optional.empty();
    }

    private Set<String> getMemberTypes(ObjectNode membersNode){

        Set<String> memberTypes = new HashSet<>();
        for (Map.Entry<String, Node> entry : membersNode.getChildren().entrySet()) {
            if (entry.getValue().isObject()){
                ObjectNode memberNode = (ObjectNode) entry.getValue();
                Optional<Node> mayBeTypeNode = memberNode.get(TYPE_PROPERTY, StringNode.class);
                if (mayBeTypeNode.isPresent()){
                    String type = ((StringNode) mayBeTypeNode.get()).getValue();
                    memberTypes.add(type);
                }
            }
        }

        return memberTypes;
    }

    private CollectorCheckingResult checkMemberTypes(Set<String> memberTypes, Set<String> knownTypes){
        return knownTypes.containsAll(memberTypes)
                ? CollectorCheckingResult.INCLUDE
                : CollectorCheckingResult.EXCLUDE;
    }
}
