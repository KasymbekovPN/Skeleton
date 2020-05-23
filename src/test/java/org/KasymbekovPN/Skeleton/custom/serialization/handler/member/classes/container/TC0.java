package org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.container;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

import java.util.Set;

@SkeletonClass
public class TC0 {

    @SkeletonMember
    public Set<String> publicProperty;

    @SkeletonMember
    protected Set<Integer> protectedProperty;

    @SkeletonMember
    private Set<Float> privateProperty;

    @SkeletonMember
    static Set<Double> staticProperty;
}
