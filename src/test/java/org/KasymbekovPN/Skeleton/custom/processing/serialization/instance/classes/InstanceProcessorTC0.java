package org.KasymbekovPN.Skeleton.custom.processing.serialization.instance.classes;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SkeletonClass(name = "InstanceProcessorTC0")
public class InstanceProcessorTC0 {

    @SkeletonMember
    private int intValue = 10;

    @SkeletonMember
    private Set<Integer> intSet = new HashSet<>(){{
        add(1);
        add(2);
    }};

    @SkeletonMember(name = "InnerInstanceProcessorTC0")
    private InnerInstanceProcessorTC0 custom11 = new InnerInstanceProcessorTC0();

    @SkeletonMember
    private Set<InnerInstanceProcessorTC0> customSet = new HashSet<>(){{
        add(new InnerInstanceProcessorTC0());
    }};

    @SkeletonMember
    private Map<Integer, Integer> intMap = new HashMap<>(){{
        put(1, 10);
        put(2, 20);
    }};

    @SkeletonMember
    private Map<Integer, InnerInstanceProcessorTC0> customMap = new HashMap<>(){{
        put(11, new InnerInstanceProcessorTC0());
    }};

    @SkeletonMember
    private Set<Integer> nullSet;
}