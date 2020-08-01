package org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.container;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

import java.util.List;
import java.util.Set;

@SkeletonClass(name = "CollectionTC")
public class CollectionTC {

    @SkeletonMember
    public Set<String> setPublic;

    @SkeletonMember
    protected Set<Integer> setProtected;

    @SkeletonMember
    private Set<Float> setPrivate;

    @SkeletonMember
    static public List<String> listPublic;

    @SkeletonMember
    static protected List<Integer> listProtected;

    @SkeletonMember
    static private List<Float> listPrivate;
}
