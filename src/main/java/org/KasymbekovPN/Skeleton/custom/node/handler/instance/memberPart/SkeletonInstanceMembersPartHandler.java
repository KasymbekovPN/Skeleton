package org.KasymbekovPN.Skeleton.custom.node.handler.instance.memberPart;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.context.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.InvocationTargetException;

public class SkeletonInstanceMembersPartHandler implements InstanceMembersPartHandler {

    @Override
    public void set(ObjectNode objectNode, String property, Object value, InstanceContext instanceContext) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Specific specific = calculateSpecificType(value);

        switch (specific){
            case NUMBER:
                objectNode.addChild(property, new NumberNode(objectNode, (Number) value));
                break;
            case STRING:
                objectNode.addChild(property, new StringNode(objectNode, (String) value));
                break;
            case BOOLEAN:
                objectNode.addChild(property, new BooleanNode(objectNode, (Boolean) value));
                break;
            case CHARACTER:
                objectNode.addChild(property, new CharacterNode(objectNode, (Character) value));
                break;
            default:
                Object oldInstance = instanceContext.attachInstance(value);
                Collector collector = instanceContext.getCollector();

                ObjectNode newNode = new ObjectNode(objectNode);
                Pair<Node, Node> old = collector.attach(newNode, newNode);

                instanceContext.getProcessor().handle(instanceContext);

                collector.attach(old.getLeft(), old.getRight());

                objectNode.addChild(property, newNode);

                instanceContext.attachInstance(oldInstance);
                break;
        }
    }

    @Override
    public void set(ArrayNode arrayNode, Object value, InstanceContext instanceContext) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Specific specific = calculateSpecificType(value);

        switch (specific){
            case NUMBER:
                arrayNode.addChild(new NumberNode(arrayNode, (Number) value));
                break;
            case STRING:
                arrayNode.addChild(new StringNode(arrayNode, (String) value));
                break;
            case BOOLEAN:
                arrayNode.addChild(new BooleanNode(arrayNode, (Boolean) value));
                break;
            case CHARACTER:
                arrayNode.addChild(new CharacterNode(arrayNode, (Character) value));
                break;
            default:
                Object oldInstance = instanceContext.attachInstance(value);
                Collector collector = instanceContext.getCollector();

                ObjectNode newNode = new ObjectNode(arrayNode);
                Pair<Node, Node> old = collector.attach(newNode, newNode);

                instanceContext.getProcessor().handle(instanceContext);

                collector.attach(old.getLeft(), old.getRight());
                arrayNode.addChild(newNode);

                instanceContext.attachInstance(oldInstance);
                break;
        }
    }

    private Specific calculateSpecificType(Object value){
        Class<?> valueType = value.getClass();
        if (valueType.equals(String.class)){
            return Specific.STRING;
        } else if (valueType.equals(Boolean.class)){
            return Specific.BOOLEAN;
        } else if (valueType.equals(Character.class)) {
            return Specific.CHARACTER;
        }
        else if (Number.class.isAssignableFrom(valueType)){
            return Specific.NUMBER;
        }

        return Specific.NON_PRIMITIVE;
    }

    private enum Specific{
        NUMBER,
        BOOLEAN,
        STRING,
        CHARACTER,
        NON_PRIMITIVE
    }
}
