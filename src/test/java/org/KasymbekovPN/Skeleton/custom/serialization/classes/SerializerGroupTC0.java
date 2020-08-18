package org.KasymbekovPN.Skeleton.custom.serialization.classes;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

import java.util.Set;

@SkeletonClass(name = "SerializerGroupTC0")
public class SerializerGroupTC0 {

    @SkeletonMember
    public int intValue;

    @SkeletonMember
    public float floatValue;

    @SkeletonMember
    private Set<Integer> setInt;
}
