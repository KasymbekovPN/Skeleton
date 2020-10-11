package org.KasymbekovPN.Skeleton.custom.node.handler.clazz.classPart;

import org.KasymbekovPN.Skeleton.lib.node.NumberNode;
import org.KasymbekovPN.Skeleton.lib.node.ObjectNode;
import org.KasymbekovPN.Skeleton.lib.node.StringNode;

import java.util.Optional;

public class SKClassHeaderPartHandler implements ClassHeaderPartHandler {

    private final String typePropertyName;
    private final String namePropertyName;
    private final String modifiersPropertyName;

    public SKClassHeaderPartHandler(String typePropertyName,
                                    String namePropertyName,
                                    String modifiersPropertyName) {
        this.typePropertyName = typePropertyName;
        this.namePropertyName = namePropertyName;
        this.modifiersPropertyName = modifiersPropertyName;
    }

    @Override
    public void setType(ObjectNode objectNode, String type) {
        objectNode.addChild(typePropertyName, new StringNode(objectNode, type));
    }

    @Override
    public void setName(ObjectNode objectNode, String name) {
        objectNode.addChild(namePropertyName, new StringNode(objectNode, name));
    }

    @Override
    public void setModifiers(ObjectNode objectNode, int modifiers) {
        objectNode.addChild(modifiersPropertyName, new NumberNode(objectNode, modifiers));
    }

    @Override
    public Optional<String> getType(ObjectNode objectNode) {
        return extractString(objectNode, typePropertyName);
    }

    @Override
    public Optional<String> getName(ObjectNode objectNode) {
        return extractString(objectNode, namePropertyName);
    }

    @Override
    public Optional<Number> getModifiers(ObjectNode objectNode) {
        return objectNode.get(modifiersPropertyName, NumberNode.ei())
                .map(node -> ((NumberNode)node).getValue());
    }

    private Optional<String> extractString(ObjectNode objectNode, String propertyName){
        return objectNode.get(propertyName, StringNode.ei())
                .map(node -> ((StringNode) node).getValue());
    }
}
