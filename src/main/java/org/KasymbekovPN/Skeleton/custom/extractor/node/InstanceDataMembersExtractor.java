package org.KasymbekovPN.Skeleton.custom.extractor.node;

import org.KasymbekovPN.Skeleton.lib.extractor.Extractor;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class InstanceDataMembersExtractor implements Extractor<Set<String>, Pair<String, ObjectNode>> {

    @Override
    public Optional<Set<String>> extract(Pair<String, ObjectNode> object) {
        String kind = object.getLeft();
        ObjectNode membersNode = object.getRight();
        Set<String> memberNames = new HashSet<>();

        for (Map.Entry<String, Node> entry : membersNode.getChildren().entrySet()) {
            String memberName = entry.getKey();
            ObjectNode memberNode = (ObjectNode) entry.getValue();

            StringNode kindNode = (StringNode) memberNode.getChildren().get("kind");
            if (kindNode.getValue().equals(kind)){
                memberNames.add(memberName);
            }
        }

        if (memberNames.size() > 0){
            return Optional.of(memberNames);
        }

        return Optional.empty();
    }
}
