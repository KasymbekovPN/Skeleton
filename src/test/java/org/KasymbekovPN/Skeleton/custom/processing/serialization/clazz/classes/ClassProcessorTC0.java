package org.KasymbekovPN.Skeleton.custom.processing.serialization.clazz.classes;

import org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.classes.InnerInstanceProcessorTC0;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

import java.util.Map;
import java.util.Set;

@SkeletonClass(name = "ClassProcessorTC0")
public class ClassProcessorTC0 {

    @SkeletonMember
    private int intValue;

    @SkeletonMember
    private int intValue1;

    @SkeletonMember
    private float floatValue;

    @SkeletonMember(name = "InnerClassProcessorTC0")
    private InnerInstanceProcessorTC0 innerInstanceProcessorTC0;

    @SkeletonMember
    private Set<Integer> intSet;

    @SkeletonMember
    private Map<Integer, Integer> intMap;
}
