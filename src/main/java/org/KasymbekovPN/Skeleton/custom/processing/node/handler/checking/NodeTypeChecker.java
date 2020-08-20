package org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.result.processing.handler.checking.NodeTypeCheckerResult;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;

public class NodeTypeChecker implements TaskHandler<Node> {

    private static final String CUSTOM = "custom";
    private static final String TYPE = "type";
    private static final String CLASS_NAME = "className";

    private final SimpleChecker<String> systemTypeChecker;
    private final CollectorPath serviceMembersPath;
    private final CollectorPath classPath;

    private Result result;
    private Set<String> notContainsMemberPath = new HashSet<>();
    private Set<String> notContainsMemberNode = new HashSet<>();
    private Set<String> customClasses = new HashSet<>();
    private Set<String> systemMemberTypes = new HashSet<>();
    private Set<String> customMemberTypes = new HashSet<>();

    public NodeTypeChecker(SimpleChecker<String> systemTypeChecker,
                           Result result,
                           CollectorPath serviceMembersPath,
                           CollectorPath classPath) {
        this.result = result;
        this.systemTypeChecker = systemTypeChecker;
        this.serviceMembersPath = serviceMembersPath;
        this.classPath = classPath;
    }

    @Override
    public Result handle(Node object, Task<Node> task) {
        clearSets();

        ObjectNode nodeGroup = (ObjectNode) object;
        for (Map.Entry<String, Node> entry : nodeGroup.getChildren().entrySet()) {
            String className = entry.getKey();
            ObjectNode classNode = (ObjectNode)entry.getValue();
            customClasses.add(className);
            Optional<List<String>> mayBeMemberPath = getMembersPath(classNode);
            if (mayBeMemberPath.isPresent()){
                Optional<ObjectNode> mayBeMemberNode = getMemberNode(classNode, mayBeMemberPath.get());
                if (mayBeMemberNode.isPresent()){
                    Pair<Set<String>, Set<String>> memberTypes = extractMemberTypes(mayBeMemberNode.get());
                    systemMemberTypes.addAll(memberTypes.getRight());
                    customMemberTypes.addAll(memberTypes.getLeft());
                } else {
                    notContainsMemberNode.add(className);
                }
            } else {
                notContainsMemberPath.add(className);
            }
        }

        return generateResult();
    }

    @Override
    public Result getHandlerResult() {
        return result;
    }

    private void clearSets(){
        notContainsMemberPath.clear();
        notContainsMemberNode.clear();
        customClasses.clear();
        systemMemberTypes.clear();
        customMemberTypes.clear();
    }

    private Optional<List<String>> getMembersPath(ObjectNode node){
        Optional<Node> mayBeMembersPathNode = node.getChild(serviceMembersPath);
        if (mayBeMembersPathNode.isPresent()){
            List<String> pathToPart = new ArrayList<>();
            ArrayNode classPathNode = (ArrayNode) mayBeMembersPathNode.get();
            for (Node child : classPathNode.getChildren()) {
                pathToPart.add(((StringNode)child).getValue());
            }

            return Optional.of(pathToPart);
        }

        return Optional.empty();
    }

    private Optional<ObjectNode> getMemberNode(ObjectNode node, List<String> path){
        classPath.setPath(path);
        classPath.setEi(ObjectNode.ei());
        Optional<Node> mayBeMemberNode = node.getChild(classPath);
        return mayBeMemberNode.map(value -> (ObjectNode) value);
    }

    private Pair<Set<String>, Set<String>> extractMemberTypes(ObjectNode memberNodes){

        MutablePair<Set<String>, Set<String>> types = new MutablePair<>(new HashSet<>(), new HashSet<>());
        for (Map.Entry<String, Node> entry : memberNodes.getChildren().entrySet()) {
            ObjectNode memberNode = (ObjectNode) entry.getValue();
            Triple<Boolean, String, String> preparedMemberNodeData = prepareMemberNodeData(memberNode);

            Boolean custom = preparedMemberNodeData.getLeft();
            String className = preparedMemberNodeData.getMiddle();
            String type = preparedMemberNodeData.getRight();
            if (custom){
                types.getLeft().add(className);
            } else {
                types.getRight().add(type);
            }
        }

        return types;
    }

    private Triple<Boolean, String, String> prepareMemberNodeData(ObjectNode memberNode){
        String type = "";
        String className = "";
        Map<String, Node> children = memberNode.getChildren();
        Boolean custom = ((BooleanNode) children.get(CUSTOM)).getValue();
        if (custom){
            className = ((StringNode) children.get(CLASS_NAME)).getValue();
        } else {
            type = ((StringNode) children.get(TYPE)).getValue();
        }

        return new MutableTriple<>(custom, className, type);
    }

    private Result generateResult(){
        result = result.createNew();
        result.setSuccess(true);
        checkMemberPath(result);
        checkMemberNode(result);
        checkDisallowedTypes(
                result,
                systemTypeChecker,
                NodeTypeCheckerResult.DISALLOWED_SYSTEM_TYPES,
                systemMemberTypes);
        checkDisallowedTypes(
                result,
                new AllowedStringChecker(customClasses),
                NodeTypeCheckerResult.DISALLOWED_CUSTOM_TYPES,
                customMemberTypes);

        return result;
    }

    private void checkMemberPath(Result result){
        if (notContainsMemberPath.size() != 0){
            result.setSuccess(false);
            result.setOptionalData(NodeTypeCheckerResult.NOT_CONTAINS_MEMBER_PATH, notContainsMemberPath);
        }
    }

    private void checkMemberNode(Result result){
        if (notContainsMemberNode.size() != 0){
            result.setSuccess(false);
            result.setOptionalData(NodeTypeCheckerResult.NOT_CONTAINS_MEMBER_NODE, notContainsMemberNode);
        }
    }

    private void checkDisallowedTypes(Result result, SimpleChecker<String> checker, String dataId, Set<String> types){
        boolean success = true;
        HashSet<String> disallowedTypes = new HashSet<>();
        for (String type : types) {
            if (!checker.check(type)){
                success = false;
                disallowedTypes.add(type);
            }
        }

        if (!success){
            result.setSuccess(false);
            result.setOptionalData(dataId, disallowedTypes);
        }
    }
}
