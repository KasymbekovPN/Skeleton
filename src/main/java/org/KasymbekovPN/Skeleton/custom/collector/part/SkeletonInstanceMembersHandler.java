package org.KasymbekovPN.Skeleton.custom.collector.part;

import org.KasymbekovPN.Skeleton.lib.collector.part.InstanceMembersHandler;
import org.KasymbekovPN.Skeleton.lib.node.*;

public class SkeletonInstanceMembersHandler implements InstanceMembersHandler {

    @Override
    public void setSpecific(ObjectNode objectNode, String property, Object value) {
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

        return Specific.UNKNOWN;
    }

    private void fillSpecificNumber(String property, Number value){

    }

    private void fillSpecificString(String property, String value){
        //< !!!
    }

    private void fillSpecificBoolean(String property, Boolean value){
        //< !!!
    }

    private void fillSpecificCharacter(String property, Character value){
        //< !!!
    }

    private enum Specific{
        NUMBER,
        BOOLEAN,
        STRING,
        CHARACTER,
        UNKNOWN
    }
}
