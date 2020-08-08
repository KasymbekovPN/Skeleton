package org.KasymbekovPN.Skeleton.custom.serialization.group.serializer;

import org.KasymbekovPN.Skeleton.lib.entity.EntityItem;

import java.util.Objects;

public class SerializerGroupEI implements EntityItem {

    private final Entity entity;

    public static EntityItem commonEI(){
        return new SerializerGroupEI(Entity.COMMON);
    }

    public SerializerGroupEI() {
        this.entity = Entity.COMMON;
    }

    public SerializerGroupEI(Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerializerGroupEI that = (SerializerGroupEI) o;
        return entity == that.entity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }

    @Override
    public String toString() {
        return "SerializerGroupEI{" +
                "entity=" + entity +
                '}';
    }

    public enum Entity{
        COMMON
    }
}
