package org.KasymbekovPN.Skeleton.custom.processing.deserialization.instance.classes;

import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonClass;
import org.KasymbekovPN.Skeleton.lib.annotation.SkeletonMember;

import java.util.Set;

@SkeletonClass(name = "Des2InstanceTC0")
public class Des2InstanceTC0 {

    @SkeletonMember
    private int intValue;

    @SkeletonMember
    private float floatValue;

    @SkeletonMember
    private double doubleValue;

    @SkeletonMember
    private boolean booleanValue;

    @SkeletonMember
    private char charValue;

    @SkeletonMember
    private String stringObject;

    @SkeletonMember
    private Boolean booleanObject;

    @SkeletonMember
    private Integer integerObject;

    @SkeletonMember
    private Float floatObject;

    @SkeletonMember
    private Double doubleObject;

    @SkeletonMember
    private Character characterObject;

    @SkeletonMember
    private Set<Integer> intSet;

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public void setStringObject(String stringObject) {
        this.stringObject = stringObject;
    }

    public void setBooleanObject(Boolean booleanObject) {
        this.booleanObject = booleanObject;
    }

    public void setIntegerObject(Integer integerObject) {
        this.integerObject = integerObject;
    }

    public void setFloatObject(Float floatObject) {
        this.floatObject = floatObject;
    }

    public void setDoubleObject(Double doubleObject) {
        this.doubleObject = doubleObject;
    }

    public void setCharacterObject(Character characterObject) {
        this.characterObject = characterObject;
    }

    public void setIntSet(Set<Integer> intSet) {
        this.intSet = intSet;
    }

    @Override
    public String toString() {
        return "Des2InstanceTC0{" +
                "intValue=" + intValue +
                ", floatValue=" + floatValue +
                ", doubleValue=" + doubleValue +
                ", booleanValue=" + booleanValue +
                ", charValue=" + charValue +
                ", stringObject='" + stringObject + '\'' +
                ", booleanObject=" + booleanObject +
                ", integerObject=" + integerObject +
                ", floatObject=" + floatObject +
                ", doubleObject=" + doubleObject +
                ", characterObject=" + characterObject +
                ", intSet=" + intSet +
                '}';
    }
}
