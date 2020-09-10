package org.KasymbekovPN.Skeleton.custom.processing.checking.serializedClassNode.classes;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

import java.util.Set;

@SkeletonClass(name = "SerTC0")
public class SerTC0 {

    @SkeletonMember
    private float floatValue;

    @SkeletonMember(name = "InnerSerTC0")
    private InnerSerTC0 innerSerTC0;

    @SkeletonMember
    private Set<Integer> intSet;
}
