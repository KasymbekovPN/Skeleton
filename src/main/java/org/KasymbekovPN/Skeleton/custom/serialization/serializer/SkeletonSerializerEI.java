package org.KasymbekovPN.Skeleton.custom.serialization.serializer;

import org.KasymbekovPN.Skeleton.lib.format.entity.EntityItem;

import java.util.Objects;

public class SkeletonSerializerEI implements EntityItem {

    private final Entity entity;

    static public EntityItem classEI(){
        return new SkeletonSerializerEI(Entity.CLASS);
    }

    static public EntityItem memberEI(){
        return new SkeletonSerializerEI(Entity.MEMBER);
    }

    static public EntityItem constructorEI(){
        return new SkeletonSerializerEI(Entity.CONSTRUCTOR);
    }

    static public EntityItem methodEI(){
        return new SkeletonSerializerEI(Entity.METHOD);
    }

    public SkeletonSerializerEI(Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkeletonSerializerEI that = (SkeletonSerializerEI) o;
        return entity == that.entity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }

    public enum Entity{
        CLASS,
        MEMBER,
        CONSTRUCTOR,
        METHOD;
    }
}
