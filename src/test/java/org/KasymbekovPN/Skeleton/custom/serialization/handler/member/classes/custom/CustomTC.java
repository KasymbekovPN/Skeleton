package org.KasymbekovPN.Skeleton.custom.serialization.handler.member.classes.custom;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

@SkeletonClass(name = "CustomTC")
public class CustomTC {

    @SkeletonMember(name = "CustomInnerTC0")
    public CustomInnerTC0 customInnerTC0;

    @SkeletonMember(name = "CustomInnerTC1")
    protected CustomInnerTC1 customInnerTC1;

    @SkeletonMember(name = "CustomInnerTC2")
    private CustomInnerTC2 customInnerTC2;

    @SkeletonMember(name = "CustomInnerTC3")
    static CustomInnerTC3 customInnerTC3;

    @SkeletonMember
    CustomInnerTC4 customInnerTC4;

    CustomInnerTC5 customInnerTC5;
}
