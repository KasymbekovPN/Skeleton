package org.KasymbekovPN.Skeleton.lib.entity.node;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;
import org.KasymbekovPN.Skeleton.lib.node.*;

import java.util.*;
import java.util.stream.Collectors;

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
    public boolean checkInstance(EntityItem instance) {
        Set<NodeEI> collect = Arrays.stream(Entity.values()).map(NodeEI::new).collect(Collectors.toSet());
        return collect.contains(instance);
    }

    @Override
    public boolean checkInstancesStrict(Collection<EntityItem> instances) {
        Set<NodeEI> collect = Arrays.stream(Entity.values()).map(NodeEI::new).collect(Collectors.toSet());
        return collect.equals(instances);
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
        return entity.toString();
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
