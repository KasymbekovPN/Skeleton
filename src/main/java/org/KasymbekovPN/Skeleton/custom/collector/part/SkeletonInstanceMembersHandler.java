package org.KasymbekovPN.Skeleton.custom.collector.part;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.data.InstanceContext;
import org.KasymbekovPN.Skeleton.lib.collector.Collector;
import org.KasymbekovPN.Skeleton.lib.collector.part.InstanceMembersHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;
import org.KasymbekovPN.Skeleton.lib.processing.processor.Processor;

//< remove into node package, interface name like ...NodeHandler
public class SkeletonInstanceMembersHandler implements InstanceMembersHandler {

    private final InstanceContext instanceContext;

    public SkeletonInstanceMembersHandler(InstanceContext instanceContext) {
        this.instanceContext = instanceContext;
    }

    @Override
    public void set(ObjectNode objectNode, String property, Object value) {
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
                InstanceContext newInstanceContext = instanceContext.createNew(value);
                Processor<InstanceContext> processor = newInstanceContext.getProcessor();
                Collector collector = newInstanceContext.getCollector();
                Node oldNode = collector.detachNode();

                processor.handle(newInstanceContext);
                Node newNode = collector.attachNode(oldNode);
                collector.reset();
                objectNode.addChild(property, newNode);
                break;
        }
    }

    @Override
    public void set(ArrayNode arrayNode, Object value) {
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
                InstanceContext newInstanceContext = instanceContext.createNew(value);
                Processor<InstanceContext> processor = newInstanceContext.getProcessor();
                Collector collector = newInstanceContext.getCollector();
                Node oldNode = collector.detachNode();

                processor.handle(newInstanceContext);
                Node newNode = collector.attachNode(oldNode);
                arrayNode.addChild(newNode);
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
