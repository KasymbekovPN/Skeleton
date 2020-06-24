package org.KasymbekovPN.Skeleton.custom.serialization.group;

import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

import java.util.Objects;

//< need test
public class SerializerGroupEI implements EntityItem {

    private Entity entity;

    private static EntityItem commonEI(){
        return new SerializerGroupEI(Entity.COMMON);
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
