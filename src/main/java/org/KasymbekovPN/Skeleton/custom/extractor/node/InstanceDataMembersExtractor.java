package org.KasymbekovPN.Skeleton.custom.extractor.node;

import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InstanceDataMembersExtractor implements Extractor<List<String>, Pair<String, ObjectNode>> {

    private final CollectorPath serviceMembersPath;
    private final CollectorPath objectPath;

    public InstanceDataMembersExtractor(CollectorPath serviceMembersPath, CollectorPath objectPath) {
        this.serviceMembersPath = serviceMembersPath;
        this.objectPath = objectPath;
    }

    @Override
    public Optional<List<String>> extract(Pair<String, ObjectNode> object) {
        String kind = object.getLeft();
        ObjectNode classNode = object.getRight();

        Optional<Node> mayBeMembersPartPath = classNode.getChild(serviceMembersPath);
        if (mayBeMembersPartPath.isPresent()){
            List<String> membersPartPath = getMembersPartPath((ArrayNode) mayBeMembersPartPath.get());
            objectPath.setEi(ObjectNode.ei());
            objectPath.setPath(membersPartPath);

            Optional<Node> mayBeMembersNode = classNode.getChild(objectPath);
            if (mayBeMembersNode.isPresent()){
                ObjectNode membersNode = (ObjectNode) mayBeMembersNode.get();
                List<String> memberNames = getMemberNames(kind, membersNode);
                if (memberNames.size() > 0){
                    return Optional.of(memberNames);
                }
            }
        }

        return Optional.empty();
    }

    private List<String> getMembersPartPath(ArrayNode arrayNode){
        ArrayList<String> membersPartPath = new ArrayList<>();
        for (Node child : arrayNode.getChildren()) {
            membersPartPath.add(((StringNode)  child).getValue());
        }

        return membersPartPath;
    }

    private List<String> getMemberNames(String kind, ObjectNode membersNode){
        ArrayList<String> memberNames = new ArrayList<>();

        for (Map.Entry<String, Node> entry : membersNode.getChildren().entrySet()) {
            String memberName = entry.getKey();
            ObjectNode memberNode = (ObjectNode) entry.getValue();

            StringNode kindNode = (StringNode) memberNode.getChildren().get("kind");
            if (kindNode.getValue().equals(kind)){
                memberNames.add(memberName);
            }
        }

        return memberNames;
    }
}
