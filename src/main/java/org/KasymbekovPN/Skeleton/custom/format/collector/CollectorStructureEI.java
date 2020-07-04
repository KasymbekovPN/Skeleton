package org.KasymbekovPN.Skeleton.custom.format.collector;

import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

import java.util.Objects;

public class CollectorStructureEI implements EntityItem {

    private final Entity entity;

    public static EntityItem classEI(){
        return new CollectorStructureEI(Entity.CLASS);
    }

    public static EntityItem membersEI(){
        return new CollectorStructureEI(Entity.MEMBERS);
    }

    public static EntityItem annotationEI(){
        return new CollectorStructureEI(Entity.ANNOTATION);
    }

    public static EntityItem constructorEI(){
        return new CollectorStructureEI(Entity.CONSTRUCTOR);
    }

    public static EntityItem methodEI(){
        return new CollectorStructureEI(Entity.METHOD);
    }

    public static EntityItem protocolEI(){
        return new CollectorStructureEI(Entity.PROTOCOL);
    }

    public CollectorStructureEI() {
        this.entity = Entity.CLASS;
    }

    public CollectorStructureEI(Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectorStructureEI that = (CollectorStructureEI) o;
        return entity == that.entity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }

    @Override
    public String toString() {
        return "CollectorStructureEI{" +
                "entity=" + entity +
                '}';
    }

    public enum Entity {
        CLASS,
        MEMBERS,
        ANNOTATION,
        CONSTRUCTOR,
        METHOD,
        PROTOCOL
    }
}
