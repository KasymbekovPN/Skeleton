package org.KasymbekovPN.Skeleton.lib.collector.node.entity;

import org.KasymbekovPN.Skeleton.lib.collector.node.*;
import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

import java.util.Objects;

public class NodeEI implements EntityItem {

    private final Entity entity;

    public static EntityItem arrayEI(){
        return new NodeEI(Entity.ARRAY);
    }

    public static EntityItem booleanEI(){
        return new NodeEI(Entity.BOOLEAN);
    }

    public static EntityItem characterEI(){
        return new NodeEI(Entity.CHARACTER);
    }

    public static EntityItem numberEI(){
        return new NodeEI(Entity.NUMBER);
    }

    public static EntityItem objectEI(){
        return new NodeEI(Entity.OBJECT);
    }

    public static EntityItem stringEI(){
        return new NodeEI(Entity.STRING);
    }

    public NodeEI() {
        this.entity = Entity.ARRAY;
    }

    public NodeEI(Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeEI nodeEI = (NodeEI) o;
        return entity == nodeEI.entity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }

    @Override
    public String toString() {
        return "NodeEI{" +
                "entity=" + entity +
                '}';
    }

    public enum Entity{
        ARRAY(ArrayNode.class),
        BOOLEAN(BooleanNode.class),
        CHARACTER(CharacterNode.class),
        NUMBER(NumberNode.class),
        OBJECT(ObjectNode.class),
        STRING(StringNode.class);

        private Class<? extends Node> clazz;

        public Class<? extends Node> getClazz() {
            return clazz;
        }

        Entity(Class<? extends Node> clazz) {
            this.clazz = clazz;
        }
    }
}
