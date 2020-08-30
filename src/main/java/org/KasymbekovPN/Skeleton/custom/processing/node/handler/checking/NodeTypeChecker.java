package org.KasymbekovPN.Skeleton.custom.processing.node.handler.checking;

import org.KasymbekovPN.Skeleton.custom.checker.AllowedStringChecker;
import org.KasymbekovPN.Skeleton.custom.result.processing.handler.checking.NodeTypeCheckerResult;
import org.KasymbekovPN.Skeleton.lib.checker.SimpleChecker;
import org.KasymbekovPN.Skeleton.lib.collector.part.ClassMembersHandler;
import org.KasymbekovPN.Skeleton.lib.collector.path.CollectorPath;
import org.KasymbekovPN.Skeleton.lib.node.ArrayNode;
import org.KasymbekovPN.Skeleton.lib.node.Node;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;
import org.KasymbekovPN.Skeleton.lib.processing.handler.TaskHandler;
import org.KasymbekovPN.Skeleton.lib.processing.task.Task;
import org.KasymbekovPN.Skeleton.lib.result.Result;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class NodeTypeChecker implements TaskHandler<Node> {

    private final SimpleChecker<String> systemTypeChecker;
    private final CollectorPath serviceMembersPath;
    private final CollectorPath classPath;
    private final ClassMembersHandler classMembersHandler;
    private final String customKind;

    private Result result;
    private Set<String> notContainsMemberPath = new HashSet<>();
    private Set<String> notContainsMemberNode = new HashSet<>();
    private Set<String> customClasses = new HashSet<>();
    private Set<String> systemMemberTypes = new HashSet<>();
    private Set<String> customMemberTypes = new HashSet<>();

    public NodeTypeChecker(SimpleChecker<String> systemTypeChecker,
                           Result result,
                           CollectorPath serviceMembersPath,
                           CollectorPath classPath,
                           ClassMembersHandler classMembersHandler,
                           String customKind) {
        this.result = result;
        this.systemTypeChecker = systemTypeChecker;
        this.serviceMembersPath = serviceMembersPath;
        this.classPath = classPath;
        this.classMembersHandler = classMembersHandler;
        this.customKind = customKind;
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
    public Result getResult() {
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

            Optional<String> mayBeKind = classMembersHandler.getKind(memberNode);
            Optional<String> mayBeClassName = classMembersHandler.getClassName(memberNode);
            if (mayBeKind.isPresent() && mayBeClassName.isPresent()){
                String kind = mayBeKind.get();
                String className = mayBeClassName.get();
                if (kind.equals(customKind)){
                    types.getLeft().add(className);
                } else {
                    types.getRight().add(className);
                }
            }
        }

        return types;
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
