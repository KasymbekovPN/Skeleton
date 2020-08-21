package org.KasymbekovPN.Skeleton.custom.collector.part;

import org.KasymbekovPN.Skeleton.lib.collector.part.ClassMembersHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkeletonClassMembersHandler implements ClassMembersHandler {

    private final String kindPropertyName;
    private final String typePropertyName;
    private final String classNamePropertyName;
    private final String modifiersPropertyName;
    private final String argumentsPropertyName;

    public SkeletonClassMembersHandler(String kindPropertyName,
                                       String typePropertyName,
                                       String classNamePropertyName,
                                       String modifiersPropertyName,
                                       String argumentsPropertyName) {
        this.kindPropertyName = kindPropertyName;
        this.typePropertyName = typePropertyName;
        this.classNamePropertyName = classNamePropertyName;
        this.modifiersPropertyName = modifiersPropertyName;
        this.argumentsPropertyName = argumentsPropertyName;
    }

    @Override
    public void setKind(ObjectNode objectNode, String kind) {
        objectNode.addChild(kindPropertyName, new StringNode(objectNode, kind));
    }

    @Override
    public void setType(ObjectNode objectNode, String type) {
        objectNode.addChild(typePropertyName, new StringNode(objectNode, type));
    }

    @Override
    public void setClassName(ObjectNode objectNode, String className) {
        objectNode.addChild(classNamePropertyName, new StringNode(objectNode, className));
    }

    @Override
    public void setModifiers(ObjectNode objectNode, int modifiers) {
        objectNode.addChild(modifiersPropertyName, new NumberNode(objectNode, modifiers));
    }

    @Override
    public void setArguments(ObjectNode objectNode, List<String> arguments) {
        ArrayNode argumentsNode = new ArrayNode(objectNode);
        for (String argument : arguments) {
            argumentsNode.addChild(new StringNode(argumentsNode, argument));
        }
        objectNode.addChild(argumentsPropertyName, argumentsNode);
    }

    @Override
    public Optional<String> getKind(ObjectNode objectNode) {
        return extractString(objectNode, kindPropertyName);
    }

    @Override
    public Optional<String> getType(ObjectNode objectNode) {
        return extractString(objectNode, typePropertyName);
    }

    @Override
    public Optional<String> getClassName(ObjectNode objectNode) {
        return extractString(objectNode, classNamePropertyName);
    }

    @Override
    public Optional<Number> getModifiers(ObjectNode objectNode) {
        return objectNode.get(modifiersPropertyName, NumberNode.ei())
                .map(node -> ((NumberNode) node).getValue());
    }

    @Override
    public Optional<List<String>> getArguments(ObjectNode objectNode) {
        Optional<Node> mayBeArgumentsNode = objectNode.get(argumentsPropertyName, ArrayNode.ei());
        if (mayBeArgumentsNode.isPresent()){
            List<String> arguments = new ArrayList<>();
            ArrayNode argumentsNode = (ArrayNode) mayBeArgumentsNode.get();
            for (Node child : argumentsNode.getChildren()) {
                arguments.add(((StringNode) child).getValue());
            }

            return Optional.of(arguments);
        }

        return Optional.empty();
    }

    private Optional<String> extractString(ObjectNode objectNode, String propertyName){
        return objectNode.get(propertyName, StringNode.ei())
                .map(node -> ((StringNode) node).getValue());
    }
}
