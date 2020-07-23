package org.KasymbekovPN.Skeleton.custom.serialization.clazz.serializer;

import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

import java.util.Objects;

public class SerializerEI implements EntityItem {

    private final Entity entity;

    static public EntityItem classEI(){
        return new SerializerEI(Entity.CLASS);
    }

    static public EntityItem memberEI(){
        return new SerializerEI(Entity.MEMBER);
    }

    static public EntityItem constructorEI(){
        return new SerializerEI(Entity.CONSTRUCTOR);
    }

    static public EntityItem methodEI(){
        return new SerializerEI(Entity.METHOD);
    }

    public SerializerEI() {
        this.entity = Entity.CLASS;
    }

    public SerializerEI(Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerializerEI that = (SerializerEI) o;
        return entity == that.entity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }

    @Override
    public String toString() {
        return "SkeletonSerializerEI{" +
                "entity=" + entity +
                '}';
    }

    public enum Entity{
        CLASS,
        MEMBER,
        CONSTRUCTOR,
        METHOD;
    }
}