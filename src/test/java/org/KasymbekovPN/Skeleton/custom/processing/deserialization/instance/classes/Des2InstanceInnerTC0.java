package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.classes;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

@SkeletonClass(name = "Des2InstanceInnerTC0")
public class Des2InstanceInnerTC0 {

    @SkeletonMember
    private int intValue;

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    @Override
    public String toString() {
        return "Des2InstanceInnerTC0{" +
                "intValue=" + intValue +
                '}';
    }
}
