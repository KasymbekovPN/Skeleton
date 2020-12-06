package org.KasymbekovPN.Skeleton.custom.node.handler.clazz.memberPart;

import org.KasymbekovPN.Skeleton.lib.node.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// TODO: 05.12.2020 change with functional interface
public class SKClassMembersPartHandler implements ClassMembersPartHandler {

    private final String kindPropertyName;
    private final String typePropertyName;
    private final String classNamePropertyName;
    private final String modifiersPropertyName;
    private final String argumentsPropertyName;

    public SKClassMembersPartHandler(String kindPropertyName,
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SKClassMembersPartHandler that = (SKClassMembersPartHandler) o;
        return Objects.equals(kindPropertyName, that.kindPropertyName) &&
                Objects.equals(typePropertyName, that.typePropertyName) &&
                Objects.equals(classNamePropertyName, that.classNamePropertyName) &&
                Objects.equals(modifiersPropertyName, that.modifiersPropertyName) &&
                Objects.equals(argumentsPropertyName, that.argumentsPropertyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kindPropertyName, typePropertyName, classNamePropertyName, modifiersPropertyName, argumentsPropertyName);
    }
}
