package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.classes;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

@SkeletonClass(name = "Des2InstanceTC0")
public class Des2InstanceTC0 {

    @SkeletonMember
    private int intValue;

    public Des2InstanceTC0(int intValue) {
        this.intValue = intValue;
    }

    @Override
    public String toString() {
        return "Des2InstanceTC0{" +
                "intValue=" + intValue +
                '}';
    }
}
